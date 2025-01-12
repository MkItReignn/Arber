package com.arber.api.impl.theoddsapi;

import com.arber.api.SportsApiClient;
import com.arber.api.datamodel.OperationContext;
import com.arber.api.exception.*;
import com.arber.api.exception.theoddsapi.TheOddsApiFetchException;
import com.arber.datamodel.*;
import com.arber.utils.ErrorHandler;
import com.arber.utils.HttpClient;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import com.arber.api.datamodel.theoddsapi.*;
import com.arber.utils.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TheOddsApiClient implements SportsApiClient {
    private final Logger LOGGER = LoggerFactory.getLogger(TheOddsApiClient.class);
    private final TheOddsApiClientKeyMappingCache theCache;
    private final RetryPolicy theStartUpRetryPolicy;
    private final RetryPolicy theLiveRetryPolicy;
    private final ErrorHandler theErrorHandler;

    public TheOddsApiClient(TheOddsApiClientKeyMappingCache aCache,
                            RetryPolicy aStartUpRetryPolicy,
                            RetryPolicy aLiveRetryPolicy,
                            ErrorHandler anErrorHandler) {
        theCache = aCache;
        theStartUpRetryPolicy = aStartUpRetryPolicy;
        theLiveRetryPolicy = aLiveRetryPolicy;
        theErrorHandler = anErrorHandler;
    }

    @Override
    public Set<Sport> fetchAvailableSports() throws ApiException {
        String myEndpoint = TheOddsApiEndpointProvider.sportsEndpoint();
        return callAndMap(myEndpoint,
                TheOddsApiSport[].class,
                anApiSports -> TheOddsApiModelMapper.mapSports(anApiSports, theErrorHandler),
                theStartUpRetryPolicy,
                OperationContext.FETCH_SPORTS);
    }

    @Override
    public Set<League> fetchAvailableLeagues() throws ApiException {
        Set<League> myLeagues = new HashSet<>();
        for (Sport aSport : fetchAvailableSports()) {
            myLeagues.addAll(fetchAvailableLeagues(aSport));
        }
        return myLeagues;
    }

    @Override
    public Set<League> fetchAvailableLeagues(Sport aSport) throws ApiException {
        String myEndpoint = TheOddsApiEndpointProvider.sportsEndpoint();
        return callAndMap(myEndpoint,
                TheOddsApiSport[].class,
                aResponse -> TheOddsApiModelMapper.mapLeagues(aResponse, aSport, theErrorHandler),
                theStartUpRetryPolicy,
                OperationContext.FETCH_LEAGUES);
    }

    @Override
    public Set<LeagueMetadata> fetchLeagueMetadata(Sport aSport) throws ApiException {
        String myEndpoint = TheOddsApiEndpointProvider.sportsEndpoint();
        return callAndMap(myEndpoint,
                TheOddsApiSport[].class,
                aResponse -> TheOddsApiModelMapper.mapLeagueMetadata(aResponse, aSport, theErrorHandler),
                theStartUpRetryPolicy,
                OperationContext.FETCH_LEAGUE_METADATA);
    }


    @Override
    public Set<EventMetadata> fetchEventMetadata() throws ApiException {
        Set<EventMetadata> myEventMetadataSet = new HashSet<>();
        final String myEndPoint = TheOddsApiEndpointProvider.sportsEndpoint();
        TheOddsApiSport[] myOddsApiSports = callOddsApiEndpoint(myEndPoint,TheOddsApiSport[].class,
                theStartUpRetryPolicy, OperationContext.FETCH_EVENT_METADATA);

        for (TheOddsApiSport myOddsApiSport : myOddsApiSports) {
            try {
                League myLeague = TheOddsApiSchemaConverter.mapTitleToLeague(myOddsApiSport.getTitle());
                myEventMetadataSet.addAll(fetchEventMetadata(myLeague));
            } catch (SchemaMappingException e) {
                LOGGER.warn("Failed to map TheOddsApiSport '{}' to League", myOddsApiSport, e);
            }
        }

        return myEventMetadataSet;
    }

    @Override
    public Set<EventMetadata> fetchEventMetadata(League aLeague) throws ApiException {
        LeagueKey myLeagueKey = theCache.getLeagueKeyForLeague(aLeague);
        String myEndpoint = TheOddsApiEndpointProvider.eventsEndpoint(myLeagueKey);
        return callAndMap(myEndpoint,
                TheOddsApiEvent[].class,
                aResponse -> TheOddsApiModelMapper.mapEventMetadata(aResponse, aLeague),
                theStartUpRetryPolicy,
                OperationContext.FETCH_EVENT_METADATA);
    }

    @Override
    public Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport) throws ApiException {
        Set<League> myLeagues = fetchAvailableLeagues(aSport);
        Map<League, Map<EventId, MarketToBookmakers>> myLeagueMarketToBookMakers = new HashMap<>();

        for (League myLeague : myLeagues) {
            myLeagueMarketToBookMakers.put(myLeague, fetchMarketToBookmakers(myLeague));
        }

        return myLeagueMarketToBookMakers;
    }


    @Override
    public Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport, Set<Region> aRegions)
            throws ApiException {
        Set<League> myLeagues = fetchAvailableLeagues(aSport);
        Map<League, Map<EventId, MarketToBookmakers>> myLeagueMarketToBookMakers = new HashMap<>();

        for (League myLeague : myLeagues) {
            myLeagueMarketToBookMakers.put(myLeague, fetchMarketToBookmakers(myLeague, aRegions));
        }

        return myLeagueMarketToBookMakers;
    }

    @Override
    public Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague) throws ApiException {
        Set<Region> myAllRegions = Set.of(Region.values());
        return new HashMap<>(fetchMarketToBookmakers(aLeague, myAllRegions));
    }

    @Override
    public Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague, Set<Region> aRegions)
            throws ApiException {
        LeagueKey myLeagueKey = theCache.getLeagueKeyForLeague(aLeague);
        String myEndpoint = TheOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegions);
        return callAndMap(myEndpoint,
                TheOddsApiOdds[].class,
                TheOddsApiModelMapper::mapMarketToBookmakers,
                theStartUpRetryPolicy,
                OperationContext.FETCH_MARKET_TO_BOOKMAKER);
    }

    @Override
    public Set<MarketType> fetchMarketTypes(EventId anEventId) throws ApiException {
        Set<Region> myAllRegions = Set.of(Region.values());
        return new HashSet<>(fetchMarketTypes(anEventId, myAllRegions));
    }

    @Override
    public Set<MarketType> fetchMarketTypes(EventId anEventId, Set<Region> aRegions) throws ApiException {
        LeagueKey myLeagueKey = theCache.getLeagueKeyForEvent(anEventId);
        String myEndpoint = TheOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegions,
                Map.of("eventIds", anEventId.theEventId()));
        return callAndMap(
                myEndpoint,
                TheOddsApiOdds[].class,
                anApiOdd -> TheOddsApiDataExtractor.extractMarketTypes(anApiOdd, theErrorHandler),
                theStartUpRetryPolicy,
                OperationContext.FETCH_MARKET_TYPES
        );
    }

    @Override
    public Set<Bookmaker> fetchBookmakers(EventId anEventId, MarketType aMarketType) throws ApiException {
        Set<Region> myAllRegions = Set.of(Region.values());
        return fetchBookmakers(anEventId, aMarketType, myAllRegions);
    }

    @Override
    public Set<Bookmaker> fetchBookmakers(EventId anEventId, MarketType aMarketType, Set<Region> aRegions)
            throws ApiException {
        LeagueKey myLeagueKey = theCache.getLeagueKeyForEvent(anEventId);
        String myEndpoint = TheOddsApiEndpointProvider.oddsEndpoint(myLeagueKey,
                aRegions, Map.of("eventIds", anEventId.theEventId()));
        return callAndMap(
                myEndpoint,
                TheOddsApiOdds[].class,
                myApiOdds -> TheOddsApiDataExtractor.extractBookmakers(myApiOdds, aMarketType),
                theStartUpRetryPolicy,
                OperationContext.FETCH_BOOKMAKERS
        );
    }

    @Override
    public List<OddsMetadata> fetchOddsMetadata(EventId anEventId, MarketType aMarketType) throws ApiException {
        Set<Region> myAllRegions = Set.of(Region.values());
        return fetchOddsMetadata(anEventId, aMarketType, myAllRegions);
    }

    @Override
    public List<OddsMetadata> fetchOddsMetadata(EventId anEventId, MarketType aMarketType, Set<Region> aRegions)
            throws ApiException {
        LeagueKey myLeagueKey = theCache.getLeagueKeyForEvent(anEventId);
        Map<String, String> myOptionalParams = Map.of(
                "eventIds", anEventId.theEventId(),
                "markets", aMarketType.name().toLowerCase()
        );
        String myEndpoint = TheOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegions, myOptionalParams);

        return callAndMap(myEndpoint,
                TheOddsApiOdds[].class,
                myApiOdds -> TheOddsApiModelMapper.mapToOddsMetadataList(myApiOdds, anEventId, myLeagueKey, aMarketType, theErrorHandler),
                theLiveRetryPolicy,
                OperationContext.FETCH_ODDS_METADATA);
    }

    private <T> T callOddsApiEndpoint(String anEndpoint, Class<T> aResponseType, RetryPolicy aRetryPolicy,
                                      OperationContext anOperationContext)
            throws ApiException {
        try {
            return HttpClient.executeGetWithRetry(anEndpoint, aResponseType, aRetryPolicy);
        } catch (IOException | InterruptedException e) {
            throw new TheOddsApiFetchException(
                    String.format("Failed to reach TheOddsApi endpoint '%s', when attempting to '%s'",
                            anEndpoint, anOperationContext), e);
        } catch (InvalidResponseException e) {
            throw new TheOddsApiFetchException(
                    String.format("Invalid response from TheOddsApi endpoint '%s', when attempting to '%s'",
                            anEndpoint, anOperationContext), e);
        } catch (TooManyRequestsException e) {
            throw new TheOddsApiFetchException(
                    String.format("Rate limit encountered from TheOddsApi endpoint '%s', when attempting to '%s'",
                            anEndpoint, anOperationContext), e);
        }
    }

    // TODO: ExecutorService .   .schedule()                 .scheduleWithFixedDelay()
    // GUI -> display pricing (calls retrievePricing via ApiClient)
    // Autotrader retrieve pricing -> via an API (internal pricing API)
    private <ExternalType, InternalType> InternalType callAndMap(
            String anEndpoint,
            Class<ExternalType> aResponseType,
            Function<ExternalType, InternalType> aMappingFunction,
            RetryPolicy aRetryPolicy,
            OperationContext anOperationContext)
            throws ApiException {

        ExternalType myExternalType = callOddsApiEndpoint(
                anEndpoint, aResponseType, aRetryPolicy,anOperationContext);
        return aMappingFunction.apply(myExternalType);
    }
}
