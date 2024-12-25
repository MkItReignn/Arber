package com.arber.model;

import com.arber.enums.MarketType;
import com.arber.enums.Sport;
import com.arber.enums.League;
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

    public Event(String aId, Sport aSport, League theLeague, String aHomeTeamName, String aAwayTeamName,
                 ZonedDateTime aCommenceTime, List<MarketType> theMarketTypesAvailable) {
        this.theId = aId;
        this.theSport = aSport;
        this.theLeague = theLeague;
        this.theHomeTeamName = aHomeTeamName;
        this.theAwayTeamName = aAwayTeamName;
        this.theCommenceTime = aCommenceTime;
        this.theMarketTypesAvailable = theMarketTypesAvailable;
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
