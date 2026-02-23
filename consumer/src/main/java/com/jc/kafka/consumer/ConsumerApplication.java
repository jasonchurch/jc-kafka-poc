package com.jc.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

/**
 * The entry point for the Kafka Consumer application.
 * <p>
 * This application starts up and listens for messages on the configured Kafka topics.
 * </p>
 */
@SpringBootApplication
@EnableKafkaStreams
public class ConsumerApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}