package com.buzz.betfrcresolver.service;

import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.external.Prop;
import com.buzz.betfrcresolver.external.PropQueryResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PropResolverService {
    private final RestTemplate restTemplate = new RestTemplate();

    public void resolvePropsForMatchEnd(MatchEndEvent event) {
        // get all Props whose parent is the match that just ended
        PropQueryResponse propResponse = getPropsForMatch(event.getEventId(), event.getMatchNum());
        List<Prop> props = propResponse.getProps();

        // for each prop, look at propType and determine win/lose state

        // create PropResolvedEvent
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
}
