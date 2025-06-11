package com.buzz.betfrcresolver.kafka;

import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.service.PropResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final PropResolverService propResolverService;

    public KafkaConsumer(PropResolverService propResolverService) {
        this.propResolverService = propResolverService;
    }

    @KafkaListener(topics = "match-end", groupId = "resolver-service")
    public void consumeEvent(@Payload MatchEndEvent event) {
//        if (!(event instanceof MatchEndEvent)) {
//            logger.error("Unable to parse object from kafka.");
//            return;
//        }
//        MatchEndEvent matchEndEvent = (MatchEndEvent) event;
        logger.info("Consumed match end event. EventId: " + event.getEventId() +
                ", match #: " + event.getMatchNum());
        propResolverService.resolvePropsForMatchEnd(event);
    }
}
