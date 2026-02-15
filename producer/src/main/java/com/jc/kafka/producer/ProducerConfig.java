package com.jc.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for the Kafka Producer application.
 * <p>
 * This class handles the definition of Kafka topics and other infrastructure beans
 * to ensure reproducible infrastructure setup.
 * </p>
 */
@Configuration
public class ProducerConfig {

    /**
     * Defines the FX Rates topic programmatically.
     * <p>
     * This ensures the topic exists with the correct partition count and replication factor
     * before the application attempts to produce messages.
     * </p>
     *
     * @param topicName the name of the topic, injected from configuration
     * @return the NewTopic bean definition
     */
    @Bean
    public NewTopic fxRatesTopic(@Value("${app.topic.fx-rates}") String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}