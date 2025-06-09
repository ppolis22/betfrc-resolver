package com.buzz.betfrcresolver.external;

public class PropValue {
    private String value;
    private String odds;

    public PropValue() {}

    public PropValue(String value, String odds) {
        this.value = value;
        this.odds = odds;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
}
