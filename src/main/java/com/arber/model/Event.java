package com.arber.model;

import com.arber.enums.Sport;
import java.time.ZonedDateTime;

public class Event {
    private final String theId;
    private final Sport theSport;
    private final String theHomeTeamName;
    private final String theAwayTeamName;
    private final ZonedDateTime theCommenceTime;

    public Event(String aId, Sport aSport, String aHomeTeamName, String aAwayTeamName, ZonedDateTime aCommenceTime) {
        this.theId = aId;
        this.theSport = aSport;
        this.theHomeTeamName = aHomeTeamName;
        this.theAwayTeamName = aAwayTeamName;
        this.theCommenceTime = aCommenceTime;
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
                "Event{id='%s', sport='%s', homeTeam='%s', awayTeam='%s', commenceTime='%s'}",
                theId, theSport, theHomeTeamName, theAwayTeamName, theCommenceTime
        );
    }
}
