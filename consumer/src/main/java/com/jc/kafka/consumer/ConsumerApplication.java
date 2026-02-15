package com.jc.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Kafka Consumer application.
 * <p>
 * This application listens to the configured Kafka topics and processes incoming messages.
 * </p>
 */
@SpringBootApplication
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