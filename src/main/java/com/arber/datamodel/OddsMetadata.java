package com.arber.model;

import java.time.ZonedDateTime;

public record OddsMetadata(
        EventId theEventId,
        LeagueKey theLeagueKey,
        MarketType theMarketType,
        Bookmaker theBookmaker,
        Participant theHomeTeam,
        Participant theAwayTeam,
        double theHomeOdds,
        double theAwayOdds,
        ZonedDateTime theLastUpdate // TODO: Make it unix time or smth like that
) {}