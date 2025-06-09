package com.buzz.betfrcresolver.evaluator.match;

import com.buzz.betfrcresolver.evaluator.PropEvaluator;
import com.buzz.betfrcresolver.evaluator.PropState;
import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.external.PropValue;

public class TotalEvaluator extends PropEvaluator {

    @Override
    protected PropState evaluatePropValue(PropValue propValue, MatchEndEvent event) {
        int total = event.getRedScore() + event.getBlueScore();

        String[] valueComps = propValue.getValue().split(" ");
        String pick = valueComps[0];
        double thresh = Double.parseDouble(valueComps[1]);
        if (pick.equals("OVER")) {
            return total > thresh ? PropState.WON : PropState.LOST;
        } else if (pick.equals("UNDER")) {
            return total < thresh ? PropState.WON : PropState.LOST;
        } else {
            return PropState.VOID;
        }
    }
}
