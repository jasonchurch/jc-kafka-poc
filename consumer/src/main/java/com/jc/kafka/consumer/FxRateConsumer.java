package com.jc.kafka.consumer;

import com.jc.kafka.common.FxRate;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Component that consumes FX Rate messages from Kafka.
 */
@Component
public class FxRateConsumer {

    private static final Logger log = LoggerFactory.getLogger(FxRateConsumer.class);

    /**
     * Listens to the FX Rates topic and logs the received messages.
     * <p>
     * The topic name and group ID are resolved from the application configuration.
     * </p>
     *
     * @param record the incoming Kafka record containing the FX Rate.
     */
    @KafkaListener(topics = "${app.topic.fx-rates}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, FxRate> record) {
        log.info("Received: Key={}, Value={}, Partition={}, Offset={}, Timestamp={}",
                record.key(),
                record.value(),
                record.partition(),
                record.offset(),
                record.timestamp());
    }
}