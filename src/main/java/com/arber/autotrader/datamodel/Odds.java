package com.arber.autotrader.datamodel;

import com.arber.datamodel.EventId;
import com.arber.datamodel.Participant;

public record Odds(
        EventId theEventId,
        Participant theHomeTeam,
        Participant theAwayTeam,
        double theHomeOdds,
        double theAwayOdds,
        long theLastUpdateMillis
) {
}
