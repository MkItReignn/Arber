package com.arber.model;

import java.time.ZonedDateTime;
import com.arber.enums.Bookmaker;
import com.arber.enums.MarketType;

public record Odds(
        String theEventId,
        String theLeagueKey,
        MarketType theMarketType,
        Bookmaker theBookmaker,
        String theHomeTeam,
        String theAwayTeam,
        double theHomeOdds,
        double theAwayOdds,
        ZonedDateTime theLastUpdate
) {}