package com.jc.kafka.common;

/**
 * Constants for Kafka topic names used across the application.
 */
public final class TopicNames {
    /**
     * The topic name for FX Rate events.
     */
    public static final String FX_RATE_TOPIC = "fx-rates";

    private TopicNames() {
        // prevent instantiation
    }
}