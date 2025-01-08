package com.arber.datamodel;

public record OddsMetadata(
        EventId theEventId,
        LeagueKey theLeagueKey,
        MarketType theMarketType,
        Bookmaker theBookmaker,
        Participant theHomeTeam,
        Participant theAwayTeam,
        double theHomeOdds,
        double theAwayOdds,
        long theLastUpdateMillis
) {}