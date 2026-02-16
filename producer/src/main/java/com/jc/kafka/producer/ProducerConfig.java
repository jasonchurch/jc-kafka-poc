package com.jc.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
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
    public NewTopic fxRatesTopic(
            @Value("${app.topic.fx-rates.name}") String topicName,
            @Value("${app.topic.fx-rates.partitions}") int partitions,
            @Value("${app.topic.fx-rates.replicas}") int replicas,
            @Value("${app.topic.fx-rates.retention-ms}") String retentionMs,
            @Value("${app.topic.fx-rates.cleanup-policy}") String cleanupPolicy) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicas)
                .config(TopicConfig.RETENTION_MS_CONFIG, retentionMs)
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, cleanupPolicy)
                .build();
    }

    /**
     * Defines the Compacted FX Rates topic.
     * <p>
     * Configured with aggressive compaction settings so the effect is visible immediately
     * during the POC.
     * </p>
     */
    @Bean
    public NewTopic fxRatesCompactedTopic(
            @Value("${app.topic.fx-rates-compacted.name}") String topicName,
            @Value("${app.topic.fx-rates-compacted.partitions}") int partitions,
            @Value("${app.topic.fx-rates-compacted.replicas}") int replicas,
            @Value("${app.topic.fx-rates-compacted.cleanup-policy}") String cleanupPolicy,
            @Value("${app.topic.fx-rates-compacted.min-cleanable-dirty-ratio}") String dirtyRatio,
            @Value("${app.topic.fx-rates-compacted.segment-ms}") String segmentMs) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicas)
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, cleanupPolicy)
                // Aggressive compaction settings for POC purposes:
                .config(TopicConfig.MIN_CLEANABLE_DIRTY_RATIO_CONFIG, dirtyRatio)
                .config(TopicConfig.SEGMENT_MS_CONFIG, segmentMs)
                .build();
    }
}