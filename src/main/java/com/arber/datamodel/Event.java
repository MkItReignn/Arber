package com.arber.datamodel;

import java.time.ZonedDateTime;
import java.util.List;

public class Event {
    private final String theId;
    private final Sport theSport;
    private final League theLeague;
    private final String theHomeTeamName;
    private final String theAwayTeamName;
    private final ZonedDateTime theCommenceTime;
    private final List<MarketType> theMarketTypesAvailable;

    public Event(String aId, Sport aSport, League aLeague, String aHomeTeamName, String aAwayTeamName,
                 ZonedDateTime aCommenceTime, List<MarketType> aMarketTypesAvailable) {
        theId = aId;
        theSport = aSport;
        theLeague = aLeague;
        theHomeTeamName = aHomeTeamName;
        theAwayTeamName = aAwayTeamName;
        theCommenceTime = aCommenceTime;
        theMarketTypesAvailable = aMarketTypesAvailable;
    }

    public String getId() {
        return theId;
    }

    public Sport getSport() {
        return theSport;
    }

    public String getHomeTeamName() {
        return theHomeTeamName;
    }

    public String getAwayTeamName() {
        return theAwayTeamName;
    }

    public ZonedDateTime getCommenceTime() {
        return theCommenceTime;
    }

    @Override
    public String toString() {
        return String.format(
                "Event{id='%s', sport='%s', league='%s', homeTeam='%s', awayTeam='%s', commenceTime='%s', marketTypesAvailable='%s'}",
                theId, theSport, theLeague, theHomeTeamName, theAwayTeamName, theCommenceTime, theMarketTypesAvailable
        );
    }
}
