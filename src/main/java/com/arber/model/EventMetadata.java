package com.arber.model;


import com.arber.enums.Sport;
import com.arber.enums.League;

import java.time.ZonedDateTime;

public record EventMetadata(
        String theEventId,
        Sport theSport,
        League theLeague,
        String theLeagueKey,
        String theHomeTeamName,
        String theAwayTeamName,
        ZonedDateTime theCommenceTime
) {
    @Override
    public String toString() {
        return String.format(
                "Event{id='%s', sport='%s', league='%s', homeTeam='%s', awayTeam='%s', commenceTime='%s'}",
                theEventId, theSport, theLeague, theHomeTeamName, theAwayTeamName, theCommenceTime
        );
    }
}

