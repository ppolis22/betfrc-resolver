package com.buzz.betfrcresolver.evaluator;

import com.buzz.betfrcresolver.evaluator.match.MoneylineEvaluator;
import com.buzz.betfrcresolver.evaluator.match.SpreadEvaluator;
import com.buzz.betfrcresolver.evaluator.match.TotalEvaluator;

import java.util.HashMap;
import java.util.Map;

public class EvaluatorFactory {
    private final static Map<String, PropEvaluator> evaluators;

    static {
        evaluators = new HashMap<>();
        evaluators.put("Moneyline", new MoneylineEvaluator());
        evaluators.put("Spread", new SpreadEvaluator());
        evaluators.put("Total", new TotalEvaluator());
    }

    public static PropEvaluator getEvaluator(String propType) {
        return evaluators.get(propType);
    }
}
