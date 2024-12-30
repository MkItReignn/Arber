package com.arber.api;

import com.arber.enums.Bookmaker;
import com.arber.enums.MarketType;
import com.arber.enums.Sport;
import com.arber.enums.League;
import com.arber.enums.Region;
import com.arber.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SportsApiClient {

    Set<Sport> fetchAvailableSports();

    Set<League> fetchAvailableLeagues();
    Set<League> fetchAvailableLeagues(Sport aSport);
    Set<LeagueMetadata> fetchAvailableLeagueMetadata(Sport aSport);

    Set<EventMetadata> fetchAvailableEvents();
    Set<EventMetadata> fetchAvailableEvents(League aLeague);

    Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport);
    Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport, Region aRegion);
    Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague);
    Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague, Region aRegion);

    Set<MarketType> fetchMarketTypes(String anEventId);
    Set<MarketType> fetchMarketTypes(String anEventId, Region aRegion);

    Set<Bookmaker> fetchBookmakers(String anEventId, MarketType aMarketType);
    Set<Bookmaker> fetchBookmakers(String anEventId, MarketType aMarketType, Region aRegion);

    List<Odds> fetchOdds(String anEventId, MarketType aMarketType);
    List<Odds> fetchOdds(String anEventId, MarketType aMarketType, Region aRegion);

    boolean isApiAvailable();
}
