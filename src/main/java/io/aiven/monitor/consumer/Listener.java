package io.aiven.monitor.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aiven.monitor.dao.MetricsEntity;
import io.aiven.monitor.dao.MetricsRepository;
import io.aiven.monitor.model.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("consumer")
public class Listener {
    private final ObjectMapper objectMapper;
    private final MetricsRepository repository;

    Logger logger = LoggerFactory.getLogger(Listener.class);

    public Listener(ObjectMapper objectMapper, MetricsRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @KafkaListener(id="metricsListener", topics = "site-metrics-topic")
    public void listen(String in) {
        try {
            logger.info("Received new metric " + in);
            Metrics metrics = objectMapper.readValue(in, Metrics.class);
            repository.save(new MetricsEntity(Long.toString(System.nanoTime()), metrics.getSiteUrl(), metrics.getResponseTime(), metrics.getStatusCode(), metrics.getMatchesRegex()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
