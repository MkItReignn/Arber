package com.arber.api.datamodel.theoddsapi;

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

    public String getBookmakerKey() {
        return theBookmakerKey;
    }

    public String getTitle() {
        return theTitle;
    }

    public String getLastUpdate() {
        return theLastUpdate;
    }

    public List<TheOddsApiMarket> getMarkets() {
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
