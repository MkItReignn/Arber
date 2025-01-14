package com.arber.datamodel;

import java.util.Optional;

public record LeagueMetadata(
        Sport theSport,
        League theLeague,
        LeagueKey theLeagueKey,
        String theName,
        Optional<String> theDescription
) {}

