package com.buzz.betfrcresolver.external;

import java.util.List;

public class Prop {
    private String id;
    private String type;
    private List<PropValue> values;

    public Prop() {}

    public Prop(String id, String type, List<PropValue> values) {
        this.id = id;
        this.type = type;
        this.values = values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropValue> getValues() {
        return values;
    }

    public void setValues(List<PropValue> values) {
        this.values = values;
    }
}
