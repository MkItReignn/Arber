package com.arber.datamodel;

public record EventMetadata(
        String theEventId,
        Sport theSport,
        League theLeague,
        String theLeagueKey,
        String theHomeTeamName,
        String theAwayTeamName,
        long theCommenceTimeMilli
) {
    @Override
    public String toString() {
        return String.format(
                "Event{id='%s', sport='%s', league='%s', homeTeam='%s', awayTeam='%s', commenceTime='%d'}",
                theEventId, theSport, theLeague, theHomeTeamName, theAwayTeamName, theCommenceTimeMilli
        );
    }
}

