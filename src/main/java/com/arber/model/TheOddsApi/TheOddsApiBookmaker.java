package com.arber.model.TheOddsApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TheOddsApiBookmaker {

    private final String theKey;
    private final String theTitle;
    private final String theLastUpdate;
    private final List<TheOddsApiMarket> theMarkets;

    @JsonCreator
    public TheOddsApiBookmaker(@JsonProperty("key") String aKey,
                               @JsonProperty("title") String aTitle,
                               @JsonProperty("last_update") String aLastUpdate,
                               @JsonProperty("markets") List<TheOddsApiMarket> aMarkets) {
        this.theKey = aKey;
        this.theTitle = aTitle;
        this.theLastUpdate = aLastUpdate;
        this.theMarkets = aMarkets;
    }

    public String getTheKey() {
        return theKey;
    }

    public String getTheTitle() {
        return theTitle;
    }

    public String getTheLastUpdate() {
        return theLastUpdate;
    }

    public List<TheOddsApiMarket> getTheMarkets() {
        return theMarkets;
    }

    @Override
    public String toString() {
        return "TheOddsApiBookmaker {" +
                "theKey='" + theKey + '\'' +
                ", theTitle='" + theTitle + '\'' +
                ", theLastUpdate='" + theLastUpdate + '\'' +
                ", theMarkets=" + theMarkets +
                '}';
    }
}
