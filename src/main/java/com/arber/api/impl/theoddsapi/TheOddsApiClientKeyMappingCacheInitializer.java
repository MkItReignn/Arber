package com.arber.api.impl.theoddsapi;

import com.arber.api.exception.InvalidResponseException;
import com.arber.api.exception.SchemaMappingException;
import com.arber.api.exception.TooManyRequestsException;
import com.arber.api.exception.theoddsapi.TheOddsApiCacheInitializationException;
import com.arber.utils.HttpClient;
import com.arber.datamodel.League;
import com.arber.datamodel.EventId;
import com.arber.datamodel.LeagueKey;
import com.arber.api.datamodel.theoddsapi.TheOddsApiEvent;
import com.arber.api.datamodel.theoddsapi.TheOddsApiSport;
import com.arber.utils.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TheOddsApiClientKeyMappingCacheInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TheOddsApiClientKeyMappingCacheInitializer.class);

    public TheOddsApiClientKeyMappingCacheInitializer() {}

    public void initializeCache(TheOddsApiClientKeyMappingCache aCache) throws TheOddsApiCacheInitializationException {
        RetryPolicy myRetryPolicy = RetryPolicy.builder()
                .withLoggingEnabled(true)
                .withExponentialBackoff(true)
                .build();
        try {
            TheOddsApiSport[] mySports = fetchSports(myRetryPolicy);
            for (TheOddsApiSport mySport : mySports) {
                LeagueKey myLeagueKey = new LeagueKey(mySport.getKey());
                mapLeague(aCache, mySport, myLeagueKey);
                fetchAndStoreEvents(aCache, myLeagueKey, myRetryPolicy);
            }
        } catch (IOException myException) {
            throw new TheOddsApiCacheInitializationException("Network error occurred during cache initialization.", myException);
        } catch (TooManyRequestsException myException) {
            throw new TheOddsApiCacheInitializationException("API rate-limiting exceeded.", myException);
        } catch (InterruptedException myException) {
            Thread.currentThread().interrupt();
            throw new TheOddsApiCacheInitializationException("Thread was interrupted during cache initialization.", myException);
        } catch (InvalidResponseException myException) {
            throw new TheOddsApiCacheInitializationException("Unrecognized response from API.", myException);
        }
    }

    private TheOddsApiSport[] fetchSports(RetryPolicy aRetryPolicy) throws IOException, TooManyRequestsException, InterruptedException, InvalidResponseException {
        String mySportsEndpoint = TheOddsApiEndpointProvider.sportsEndpoint();
        return HttpClient.executeGetWithRetry(mySportsEndpoint, TheOddsApiSport[].class, aRetryPolicy);
    }

    private void mapLeague(TheOddsApiClientKeyMappingCache aCache, TheOddsApiSport aSport, LeagueKey aLeagueKey) {
        try {
            League myLeague = TheOddsApiSchemaConverter.mapTitleToLeague(aSport.getTitle());
            aCache.addLeagueToKeyMapping(myLeague, aLeagueKey);
        } catch (SchemaMappingException myException) {
            LOGGER.warn("No matching league representation for title '{}'", aSport.getTitle());
        }
    }

    private void fetchAndStoreEvents(TheOddsApiClientKeyMappingCache aCache, LeagueKey aLeagueKey, RetryPolicy aRetryPolicy)
            throws IOException, TooManyRequestsException, InterruptedException, InvalidResponseException {

        String myEventsEndpoint = TheOddsApiEndpointProvider.eventsEndpoint(aLeagueKey);
        TheOddsApiEvent[] myEvents =
                HttpClient.executeGetWithRetry(myEventsEndpoint, TheOddsApiEvent[].class, aRetryPolicy);

        for (TheOddsApiEvent myEvent : myEvents) {
            aCache.addEventToLeagueMapping(new EventId(myEvent.getId()), aLeagueKey);
        }
    }
}