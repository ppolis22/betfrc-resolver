package com.buzz.betfrcresolver.evaluator;

public enum PropState {
    WON("W"),
    LOST("L"),
    VOID("V");

    private String value;

    private PropState(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
