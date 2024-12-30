package com.arber.model.TheOddsApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheOddsApiEvent {

    private final String theId;
    private final String theSportKey;
    private final String theSportTitle;
    private final String theCommenceTime;
    private final String theHomeTeam;
    private final String theAwayTeam;
    private final boolean theHasOutrights;

    @JsonCreator
    public TheOddsApiEvent(@JsonProperty("id") String aId,
                           @JsonProperty("sport_key") String aSportKey,
                           @JsonProperty("sport_title") String aSportTitle,
                           @JsonProperty("commence_time") String aCommenceTime,
                           @JsonProperty("home_team") String aHomeTeam,
                           @JsonProperty("away_team") String aAwayTeam,
                           @JsonProperty("has_outrights") boolean aHasOutrights) {
        this.theId = aId;
        this.theSportKey = aSportKey;
        this.theSportTitle = aSportTitle;
        this.theCommenceTime = aCommenceTime;
        this.theHomeTeam = aHomeTeam;
        this.theAwayTeam = aAwayTeam;
        this.theHasOutrights = aHasOutrights;
    }

    public String getTheId() {
        return theId;
    }

    public String getTheSportKey() {
        return theSportKey;
    }

    public String getTheSportTitle() {
        return theSportTitle;
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

    public boolean getTheHasOutrights() {
        return theHasOutrights;
    }

    @Override
    public String toString() {
        return "TheOddsApiEvent {" +
                "theId='" + theId + '\'' +
                ", theSportKey='" + theSportKey + '\'' +
                ", theSportTitle='" + theSportTitle + '\'' +
                ", theCommenceTime='" + theCommenceTime + '\'' +
                ", theHomeTeam='" + theHomeTeam + '\'' +
                ", theAwayTeam='" + theAwayTeam + '\'' +
                ", theHasOutrights=" + theHasOutrights +
                '}';
    }
}
