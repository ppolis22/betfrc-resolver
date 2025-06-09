package com.buzz.betfrcresolver.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(MatchEndEvent event) {
        try {
            kafkaTemplate.send("match-end", event);
            logger.info("Wrote match end event to queue: " + event.getEventId());
        } catch (Exception e) {
            logger.error("Unable to write match end event to queue.", e);
        }
    }
}
