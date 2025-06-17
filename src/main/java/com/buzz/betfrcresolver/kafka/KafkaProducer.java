package com.buzz.betfrcresolver.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, PropResolvedEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PropResolvedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(PropResolvedEvent event) {
        try {
            kafkaTemplate.send("prop-resolution", event);
            logger.info("Wrote prop resolution event to queue: " + event.getPropId() +
                    " with status: " + event.getState());
        } catch (Exception e) {
            logger.error("Unable to write match end event to queue.", e);
        }
    }
}
