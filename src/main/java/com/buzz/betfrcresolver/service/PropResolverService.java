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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class PropResolverService {
    private static final Logger logger = LoggerFactory.getLogger(PropResolverService.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaProducer kafkaProducer;

    @Value("${buzz.app.odds-service}")
    private String oddsService;

    public PropResolverService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        this.restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }
        });
    }

    public void resolvePropsForMatchEnd(MatchEndEvent event) {
        // get all Props whose parent is the match that just ended
        ResponseEntity<PropQueryResponse> propResponse = getPropsForMatch(
                event.getEventId(), event.getMatchNum());
        if (!propResponse.getStatusCode().is2xxSuccessful() || propResponse.getBody() == null) {
            logger.error("Error fetching props for event.");
            return;
        }

        List<Prop> props = propResponse.getBody().getProps();
        logger.info("Retrieved " + props.size() + " props to resolve.");

        // for each prop, look at propType and determine win/lose state
        for (Prop prop : props) {
            String propType = prop.getType();
            PropEvaluator evaluator = EvaluatorFactory.getEvaluator(propType);
            List<PropResolvedEvent> resolvedEvents = evaluator.evaluate(prop, event);
            sendResolvedEvents(resolvedEvents);
        }
    }

    private ResponseEntity<PropQueryResponse> getPropsForMatch(String eventId, Integer matchNum) {
        String url = "http://" + oddsService + "/props/event/" + eventId + "/match/" + matchNum;
        logger.info("Fetching props for target match, at: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> emptyRequestEntity = new HttpEntity<>(headers);

        ResponseEntity<PropQueryResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                emptyRequestEntity,
                PropQueryResponse.class);

        logger.info("Props fetch response: " + response.getStatusCode());
        return response;
    }

    private void sendResolvedEvents(List<PropResolvedEvent> events) {
        logger.info("Writing Prop Resolved Event(s)");
        for (PropResolvedEvent event : events) {
            kafkaProducer.sendEvent(event);
        }
    }
}
