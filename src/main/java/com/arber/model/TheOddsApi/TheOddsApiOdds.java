package com.arber.model.TheOddsApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TheOddsApiOdds {

    private final String theId;
    private final Boolean theHasOutrights;
    private final String theSportKey;
    private final String theSportsTitle;
    private final String theCommenceTime;
    private final String theHomeTeam;
    private final String theAwayTeam;
    private final List<TheOddsApiBookmaker> theBookmakers;

    @JsonCreator
    public TheOddsApiOdds(@JsonProperty("id") String aId,
                          @JsonProperty("has_outrights") Boolean aHasOutrights,
                          @JsonProperty("sport_key") String aSportKey,
                          @JsonProperty("sport_title") String aSportTitle,
                          @JsonProperty("commence_time") String aCommenceTime,
                          @JsonProperty("home_team") String aHomeTeam,
                          @JsonProperty("away_team") String aAwayTeam,
                          @JsonProperty("bookmakers") List<TheOddsApiBookmaker> aBookmakers) {
        theId = aId;
        theHasOutrights = aHasOutrights;
        theSportKey = aSportKey;
        theSportsTitle = aSportTitle;
        theCommenceTime = aCommenceTime;
        theHomeTeam = aHomeTeam;
        theAwayTeam = aAwayTeam;
        theBookmakers = aBookmakers;
    }


    public String getTheId() {
        return theId;
    }

    public Boolean getTheHasOutrights() {
        return theHasOutrights;
    }

    public String getTheSportKey() {
        return theSportKey;
    }

    public String getTheSportsTitle() {
        return theSportsTitle;
    }

    public String getTheCommenceTime() {
        return theCommenceTime;
    }

    public String getTheHomeTeam() {
        return theHomeTeam;
    }

    public String getTheAwayTeam() {
        return theAwayTeam;
    }

    public List<TheOddsApiBookmaker> getTheBookmakers() {
        return theBookmakers;
    }

    @Override
    public String toString() {
        return "TheOddsApiOdds {" +
                "theId='" + theId + '\'' +
                ", theSportKey='" + theSportKey + '\'' +
                ", theCommenceTime='" + theCommenceTime + '\'' +
                ", theHomeTeam='" + theHomeTeam + '\'' +
                ", theAwayTeam='" + theAwayTeam + '\'' +
                ", theBookmakers=" + theBookmakers +
                '}';
    }
}
