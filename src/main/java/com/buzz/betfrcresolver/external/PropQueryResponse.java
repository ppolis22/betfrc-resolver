package com.buzz.betfrcresolver.external;

import java.util.List;

public class PropQueryResponse {
    private List<Prop> props;

    public PropQueryResponse() {}

    public PropQueryResponse(List<Prop> props) {
        this.props = props;
    }

    public List<Prop> getProps() {
        return props;
    }

    public void setProps(List<Prop> props) {
        this.props = props;
    }
}
