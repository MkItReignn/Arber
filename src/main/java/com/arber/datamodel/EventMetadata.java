package com.arber.datamodel;

public record EventMetadata(
        EventId theEventId,
        Sport theSport,
        League theLeague,
        LeagueKey theLeagueKey,
        String theHomeTeamName,
        String theAwayTeamName,
        long theCommenceTimeMilli
) {
    @Override
    public String toString() {
        return String.format(
                "Event{id='%s', sport='%s', theLeague='%s', homeTeam='%s', awayTeam='%s', commenceTime='%d'}",
                theEventId, theSport, theLeague, theHomeTeamName, theAwayTeamName, theCommenceTimeMilli
        );
    }
}

