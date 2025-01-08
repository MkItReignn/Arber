package com.arber.api.datamodel.theoddsapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TheOddsApiMarket {

    private final String theMarketKey;
    private final String theLastUpdate;
    private final List<TheOddsApiOutcome> theOutcomes;

    @JsonCreator
    public TheOddsApiMarket(@JsonProperty("key") String aMarketKey,
                            @JsonProperty("last_update") String aLastUpdate,
                            @JsonProperty("outcomes") List<TheOddsApiOutcome> aOutcomes) {
        theMarketKey = aMarketKey;
        theLastUpdate = aLastUpdate;
        theOutcomes = aOutcomes;
    }

    public String getMarketKey() {
        return theMarketKey;
    }

    public String getLastUpdate() {
        return theLastUpdate;
    }

    public List<TheOddsApiOutcome> getOutcomes() {
        return theOutcomes;
    }

    @Override
    public String toString() {
        return "TheOddsApiMarket {" +
                "theKey='" + theMarketKey + '\'' +
                ", theOutcomes=" + theOutcomes +
                '}';
    }
}
