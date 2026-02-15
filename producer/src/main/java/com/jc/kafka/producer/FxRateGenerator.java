package com.jc.kafka.producer;

import com.jc.kafka.common.FxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A CommandLineRunner that generates random FX rates and sends them to a Kafka topic.
 * <p>
 * This component simulates a live market by continuously updating FX rates for specific pairs
 * (e.g., USD_CAD, EUR_CAD) and publishing the updates to the configured Kafka topic.
 * </p>
 */
@Component
public class FxRateGenerator implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FxRateGenerator.class);

    private final KafkaTemplate<String, FxRate> kafkaTemplate;
    private final String topicName;
    private final Random random = new Random();
    private final Map<String, BigDecimal> currentRates = new HashMap<>();

    /**
     * Constructs a new FxRateGenerator.
     *
     * @param kafkaTemplate the KafkaTemplate used to send messages to Kafka.
     * @param topicName     the target Kafka topic name injected from configuration.
     */
    public FxRateGenerator(KafkaTemplate<String, FxRate> kafkaTemplate,
                           @Value("${app.topic.fx-rates}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        // Initialize starting rates
        currentRates.put("USD_CAD", new BigDecimal("1.3500"));
        currentRates.put("EUR_CAD", new BigDecimal("1.4500"));
        currentRates.put("GBP_CAD", new BigDecimal("1.7000"));
        currentRates.put("AUD_CAD", new BigDecimal("0.8800"));
    }

    /**
     * Starts the FX rate generation loop.
     * <p>
     * This method runs indefinitely, updating rates and sending messages every second.
     * </p>
     *
     * @param args incoming main method arguments
     * @throws Exception if an error occurs during execution
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting FX Rate Generator targeting topic: {}", topicName);
        
        while (true) {
            for (Map.Entry<String, BigDecimal> entry : currentRates.entrySet()) {
                String pair = entry.getKey();
                BigDecimal currentRate = entry.getValue();

                // Simulate market movement: Fluctuate rate by +/- 0.0005
                BigDecimal change = new BigDecimal(random.nextDouble() * 0.0010 - 0.0005);
                BigDecimal newRate = currentRate.add(change).setScale(4, RoundingMode.HALF_UP);
                
                // Update state
                currentRates.put(pair, newRate);

                // Calculate inverse for completeness
                BigDecimal inverseRate = BigDecimal.ONE.divide(newRate, 4, RoundingMode.HALF_UP);

                FxRate fxRate = new FxRate(
                    pair,
                    newRate,
                    inverseRate,
                    Instant.now()
                );

                // Send to Kafka
                // Key = pair (Critical for Log Compaction)
                kafkaTemplate.send(topicName, pair, fxRate)
                    .whenComplete((result, ex) -> {
                        if (ex != null) log.error("Error sending {}: {}", pair, ex.getMessage(), ex);
                        else log.info("Sent: {}", fxRate);
                    });
            }
            Thread.sleep(1000); // Wait 1 second before next tick
        }
    }
}