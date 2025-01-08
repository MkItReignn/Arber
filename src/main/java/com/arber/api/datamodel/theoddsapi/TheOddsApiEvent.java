package com.arber.api.datamodel.theoddsapi;

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
        theId = aId;
        theSportKey = aSportKey;
        theSportTitle = aSportTitle;
        theCommenceTime = aCommenceTime;
        theHomeTeam = aHomeTeam;
        theAwayTeam = aAwayTeam;
        theHasOutrights = aHasOutrights;
    }

    public String getId() {
        return theId;
    }

    public String getSportKey() {
        return theSportKey;
    }

    public String getSportTitle() {
        return theSportTitle;
    }

    public String getCommenceTime() {
        return theCommenceTime;
    }

    public String getHomeTeam() {
        return theHomeTeam;
    }

    public String getAwayTeam() {
        return theAwayTeam;
    }

    public boolean getHasOutrights() {
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
