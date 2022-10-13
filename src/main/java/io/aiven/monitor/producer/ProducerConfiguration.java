package io.aiven.monitor.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Profile("producer")
@EnableScheduling
public class ProducerConfiguration {
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("site-metrics-topic")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
