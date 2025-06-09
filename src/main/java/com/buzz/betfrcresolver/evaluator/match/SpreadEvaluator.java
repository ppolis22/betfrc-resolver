package com.buzz.betfrcresolver.evaluator.match;

import com.buzz.betfrcresolver.evaluator.PropEvaluator;
import com.buzz.betfrcresolver.evaluator.PropState;
import com.buzz.betfrcresolver.external.MatchEndEvent;
import com.buzz.betfrcresolver.external.PropValue;

public class SpreadEvaluator extends PropEvaluator {

    @Override
    protected PropState evaluatePropValue(PropValue propValue, MatchEndEvent event) {
        int diff = event.getRedScore() - event.getBlueScore();

        String[] valueComps = propValue.getValue().split(" ");
        String pick = valueComps[0];
        double thresh = Double.parseDouble(valueComps[1]);

        if (pick.equals("RED")) {
            if (diff + thresh > 0) return PropState.WON;
            if (diff + thresh < 0) return PropState.LOST;
        } else if (pick.equals("BLUE")) {
            if (diff - thresh < 0) return PropState.WON;
            if (diff - thresh > 0) return PropState.LOST;
        }
        return PropState.VOID;
    }
}