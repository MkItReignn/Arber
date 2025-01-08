package com.arber.model;

import java.util.Optional;

public record LeagueMetadata(
        Sport theSport,
        League theLeague,
        String theLeagueKey,
        String theName,
        Optional<String> theDescription
) {}

