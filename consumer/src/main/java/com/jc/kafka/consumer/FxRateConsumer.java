package com.jc.kafka.consumer;

import com.jc.kafka.common.FxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consumer component that listens for FX Rate updates.
 */
@Component
public class FxRateConsumer {

    private static final Logger log = LoggerFactory.getLogger(FxRateConsumer.class);

    /**
     * Listens to the standard FX Rates topic (Delete policy).
     *
     * @param rate the deserialized FxRate object
     * @param key  the Kafka record key (e.g., "USD_CAD")
     */
    @KafkaListener(topics = "${app.topic.fx-rates.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeStandard(@Payload FxRate rate, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received [Standard]: Key={}, Rate={}", key, rate);
    }

    /**
     * Listens to the Compacted FX Rates topic.
     * <p>
     * We use a distinct groupId suffix ("-compacted") to ensure this listener maintains
     * its own offset management independent of the standard listener.
     * </p>
     *
     * @param rate the deserialized FxRate object
     * @param key  the Kafka record key
     */
    @KafkaListener(topics = "${app.topic.fx-rates-compacted.name}", groupId = "${spring.kafka.consumer.group-id}-compacted")
    public void consumeCompacted(@Payload FxRate rate, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received [Compacted]: Key={}, Rate={}", key, rate);
    }
}