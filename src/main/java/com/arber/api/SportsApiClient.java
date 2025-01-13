package com.arber.api;

import com.arber.api.exception.ApiException;
import com.arber.datamodel.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SportsApiClient {

    Set<Sport> fetchAvailableSports() throws ApiException;

    Set<League> fetchAvailableLeagues() throws ApiException;
    Set<League> fetchAvailableLeagues(Sport aSport) throws ApiException;
    Set<LeagueMetadata> fetchLeagueMetadata(Sport aSport) throws ApiException;

    Set<EventMetadata> fetchEventMetadata() throws ApiException;
    Set<EventMetadata> fetchEventMetadata(League aLeague) throws ApiException;

    Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport) throws ApiException;
    Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport, Set<Region> aRegions) throws ApiException;
    Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague) throws ApiException;
    Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague, Set<Region> aRegions) throws ApiException;

    Set<MarketType> fetchMarketTypes(EventId anEventId) throws ApiException;
    Set<MarketType> fetchMarketTypes(EventId anEventId, Set<Region> aRegions) throws ApiException;

    Set<Bookmaker> fetchBookmakers(EventId anEventId, MarketType aMarketType) throws ApiException;
    Set<Bookmaker> fetchBookmakers(EventId anEventId, MarketType aMarketType, Set<Region> aRegions) throws ApiException;

    List<OddsMetadata> fetchOddsMetadata(League aLeague, MarketType aMarketType) throws ApiException;
    List<OddsMetadata> fetchOddsMetadata(League aLeague, MarketType aMarketType, Set<Region> aRegion) throws ApiException;
    List<OddsMetadata> fetchOddsMetadata(EventId anEventId, MarketType aMarketType) throws ApiException;
    List<OddsMetadata> fetchOddsMetadata(EventId anEventId, MarketType aMarketType, Set<Region> aRegions) throws ApiException;
}
