package io.aiven.monitor.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.aiven.monitor.model.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Profile("producer")
public class Scheduler {
    Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private final ObjectMapper objectMapper;
    private final String regex;
    private final String url;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Scheduler(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper,
                     @Value("${monitor.url}") String url, @Value("${monitor.regex:#{null}}") String regex) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.regex = !Objects.equals(regex, "") ? regex : null;
        this.url = url;
        logger.info("Regex: " + regex);
        logger.info("Url: " + url);
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void cronJob() {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Requesting " + url);
        long startTime = System.nanoTime();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        long endTime = System.nanoTime();
        logger.info("Response time in nano seconds: " + (endTime - startTime));
        logger.info("Status code: " + response.getStatusCode());
        Boolean matchesRegex = null;
        if (regex != null) {
            String body = response.getBody();
            matchesRegex = false;
            if (body != null) {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(body);
                matchesRegex = m.find();
            }
            logger.info("Matches regex: " + matchesRegex);
        }
        Metrics metrics = new Metrics(url, endTime - startTime, response.getStatusCode().value(), matchesRegex);
        try {
            String data = objectMapper.writeValueAsString(metrics);
            kafkaTemplate.send("site-metrics-topic", Long.toString(startTime), data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
