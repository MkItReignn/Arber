package com.arber.model;

import java.util.Optional;

import com.arber.enums.Sport;
import com.arber.enums.League;

public record LeagueMetadata(
        Sport theSport,
        League theLeague,
        String theLeagueKey,
        String theName,
        Optional<String> theDescription
) {}

