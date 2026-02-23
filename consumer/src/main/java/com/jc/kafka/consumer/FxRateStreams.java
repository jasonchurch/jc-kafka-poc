package com.jc.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.kafka.common.FxRate;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

/**
 * Defines the Kafka Streams topology for the application.
 * <p>
 * This component builds a KTable from the FX Rates topic, effectively creating
 * a local state store of the latest rates for every currency pair.
 * </p>
 */
@Configuration
public class FxRateStreams {

    private static final Logger log = LoggerFactory.getLogger(FxRateStreams.class);

    @Bean
    public KTable<String, FxRate> fxRateTable(StreamsBuilder streamsBuilder,
                                              @Value("${app.topic.fx-rates-statestore.name}") String topicName,
                                              ObjectMapper objectMapper) {

        log.info("Initializing KTable topology for topic: {}", topicName);

        // Use the Spring-managed ObjectMapper to ensure support for Java 8 Time (Instant)
        JsonSerde<FxRate> fxRateSerde = new JsonSerde<>(FxRate.class, objectMapper);

        // Create a KTable from the topic.
        // A KTable is a changelog stream: it represents the latest state of the data.
        KTable<String, FxRate> fxRateTable = streamsBuilder.table(
                topicName,
                Consumed.with(Serdes.String(), fxRateSerde),
                Materialized.as("fx-rate-store") // Name of the local RocksDB store
        );

        // For demonstration, stream the updates from the table to the log.
        // In a real app, you would query 'fx-rate-store' interactively.
        fxRateTable.toStream()
                .foreach((key, value) -> log.info("State Store [KTable] Update: {} -> {}", key, value));

        return fxRateTable;
    }
}