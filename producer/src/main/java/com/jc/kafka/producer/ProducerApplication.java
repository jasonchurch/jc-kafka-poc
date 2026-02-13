package com.jc.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Kafka Producer application.
 * <p>
 * This application starts up without a web server and uses {@link FxRateGenerator}
 * to produce messages to Kafka.
 * </p>
 */
@SpringBootApplication
public class ProducerApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}