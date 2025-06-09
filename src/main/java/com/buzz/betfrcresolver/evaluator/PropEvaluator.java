package com.buzz.betfrcresolver.evaluator;

import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.external.Prop;
import com.buzz.betfrcresolver.external.PropValue;
import com.buzz.betfrcresolver.kafka.PropResolvedEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class PropEvaluator {
    public List<PropResolvedEvent> evaluate(Prop prop, MatchEndEvent event) {
        List<PropResolvedEvent> events = new ArrayList<>();
        for (PropValue propValue : prop.getValues()) {
            PropState state = evaluatePropValue(propValue, event);
            events.add(new PropResolvedEvent(prop.getId(), propValue.getValue(), state.getValue()));
        }
        return events;
    }

    protected abstract PropState evaluatePropValue(PropValue propValue, MatchEndEvent event);
}
