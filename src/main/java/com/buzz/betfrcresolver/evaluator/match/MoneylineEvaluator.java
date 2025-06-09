package com.buzz.betfrcresolver.evaluator.match;

import com.buzz.betfrcresolver.evaluator.PropEvaluator;
import com.buzz.betfrcresolver.evaluator.PropState;
import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.external.PropValue;

public class MoneylineEvaluator extends PropEvaluator {

    @Override
    protected PropState evaluatePropValue(PropValue propValue, MatchEndEvent event) {
        boolean isTie = event.getRedScore() == event.getBlueScore();
        boolean redWins = event.getRedScore() > event.getBlueScore();

        if (isTie) return PropState.VOID;
        if (propValue.getValue().equals("RED")) {
            return redWins ? PropState.WON : PropState.LOST;
        } else if (propValue.getValue().equals("BLUE")) {
            return redWins ? PropState.LOST : PropState.WON;
        } else {
            return PropState.VOID;
        }
    }
}
