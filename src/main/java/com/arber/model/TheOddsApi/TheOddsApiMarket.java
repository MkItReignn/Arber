package com.arber.model.TheOddsApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TheOddsApiMarket {

    private final String theKey;
    private final String theLastUpdate;
    private final List<TheOddsApiOutcome> theOutcomes;

    @JsonCreator
    public TheOddsApiMarket(@JsonProperty("key") String aKey,
                            @JsonProperty("last_update") String aLastUpdate,
                            @JsonProperty("outcomes") List<TheOddsApiOutcome> aOutcomes) {
        this.theKey = aKey;
        theLastUpdate = aLastUpdate;
        this.theOutcomes = aOutcomes;
    }

    public String getTheKey() {
        return theKey;
    }

    public String getTheLastUpdate() {
        return theLastUpdate;
    }

    public List<TheOddsApiOutcome> getTheOutcomes() {
        return theOutcomes;
    }

    @Override
    public String toString() {
        return "TheOddsApiMarket {" +
                "theKey='" + theKey + '\'' +
                ", theOutcomes=" + theOutcomes +
                '}';
    }
}
