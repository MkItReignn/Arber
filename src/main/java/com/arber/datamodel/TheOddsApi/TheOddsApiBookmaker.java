package com.arber.model.TheOddsApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TheOddsApiBookmaker {

    private final String theBookmakerKey;
    private final String theTitle;
    private final String theLastUpdate;
    private final List<TheOddsApiMarket> theMarkets;

    @JsonCreator
    public TheOddsApiBookmaker(@JsonProperty("key") String aBookmakerKey,
                               @JsonProperty("title") String aTitle,
                               @JsonProperty("last_update") String aLastUpdate,
                               @JsonProperty("markets") List<TheOddsApiMarket> aMarkets) {
        theBookmakerKey = aBookmakerKey;
        theTitle = aTitle;
        theLastUpdate = aLastUpdate;
        theMarkets = aMarkets;
    }

    public String getTheBookmakerKey() {
        return theBookmakerKey;
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
                "theBookmakerKey='" + theBookmakerKey + '\'' +
                ", theTitle='" + theTitle + '\'' +
                ", theLastUpdate='" + theLastUpdate + '\'' +
                ", theMarkets=" + theMarkets +
                '}';
    }
}
