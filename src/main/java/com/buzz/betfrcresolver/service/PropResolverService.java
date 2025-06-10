package com.buzz.betfrcresolver.service;

import com.buzz.betfrcresolver.evaluator.EvaluatorFactory;
import com.buzz.betfrcresolver.evaluator.PropEvaluator;
import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.external.Prop;
import com.buzz.betfrcresolver.external.PropQueryResponse;
import com.buzz.betfrcresolver.kafka.KafkaProducer;
import com.buzz.betfrcresolver.kafka.PropResolvedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PropResolverService {
    private static final Logger logger = LoggerFactory.getLogger(PropResolverService.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaProducer kafkaProducer;

    public PropResolverService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void resolvePropsForMatchEnd(MatchEndEvent event) {
        // get all Props whose parent is the match that just ended
        PropQueryResponse propResponse = getPropsForMatch(event.getEventId(), event.getMatchNum());
        List<Prop> props = propResponse.getProps();
        logger.info("Retrieved " + props.size() + " props to resolve.");

        // for each prop, look at propType and determine win/lose state
        for (Prop prop : props) {
            String propType = prop.getType();
            PropEvaluator evaluator = EvaluatorFactory.getEvaluator(propType);
            List<PropResolvedEvent> resolvedEvents = evaluator.evaluate(prop, event);
            sendResolvedEvents(resolvedEvents);
        }
    }

    private PropQueryResponse getPropsForMatch(String eventId, Integer matchNum) {
        String url = "http://localhost:8080/props/event/" + eventId + "/match/" + matchNum;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> emptyRequestEntity = new HttpEntity<>(headers);

        ResponseEntity<PropQueryResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                emptyRequestEntity,
                PropQueryResponse.class);

        // TODO validate
        return response.getBody();
    }

    private void sendResolvedEvents(List<PropResolvedEvent> events) {
        for (PropResolvedEvent event : events) {
            kafkaProducer.sendEvent(event);
        }
    }
}
