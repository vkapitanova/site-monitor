package io.aiven.monitor.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aiven.monitor.model.Metrics;
import io.aiven.monitor.dao.MetricsEntity;
import io.aiven.monitor.dao.MetricsRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("consumer")
public class Listener {
    private final ObjectMapper objectMapper;
    private final MetricsRepository repository;

    public Listener(ObjectMapper objectMapper, MetricsRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @KafkaListener(id="metricsListener", topics = "site-metrics-topic")
    public void listen(String in) {
        try {
            Metrics metrics = objectMapper.readValue(in, Metrics.class);
            repository.save(new MetricsEntity(Long.toString(System.nanoTime()), metrics.getSiteUrl(), metrics.getResponseTime(), metrics.getStatusCode(), metrics.getMatchesRegex()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
