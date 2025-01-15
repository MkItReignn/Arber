package com.arber.events.feed.poller;

import com.arber.api.SportsApiClient;
import com.arber.api.exception.ApiException;
import com.arber.cache.MetadataCache;
import com.arber.datamodel.*;
import com.arber.events.datamodel.EventMetadataUpdateEvent;
import com.arber.events.datamodel.MarketToBookmakersUpdateEvent;
import com.arber.events.datamodel.OddsMetadataUpdateEvent;
import com.arber.events.eventbus.EventBus;
import com.arber.events.feed.config.UniversalFeedServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class UniversalFeedPoller implements FeedPoller {
    private final Logger theLogger = LoggerFactory.getLogger(UniversalFeedPoller.class);
    private final SportsApiClient theSportsApiClient;
    private final EventBus theEventBus;
    private final MetadataCache theMetadataCache;
    private final ConcurrentMap<OddsMetadataId, OddsMetadata> theOddsMetadataCache;
    private final UniversalFeedServiceConfig theConfig;

    public UniversalFeedPoller(SportsApiClient aSportsApiClient,
                               EventBus anEventBus,
                               MetadataCache aMetadataCache,
                               ConcurrentMap<OddsMetadataId, OddsMetadata> anOddsMetadataCache,
                               UniversalFeedServiceConfig aConfig) {
        this.theSportsApiClient = aSportsApiClient;
        this.theEventBus = anEventBus;
        this.theMetadataCache = aMetadataCache;
        this.theOddsMetadataCache = anOddsMetadataCache;
        this.theConfig = aConfig;
    }

    @Override
    public void pollMetadata() {
        try {
            processEventMetadata();
            processMarketToBookmakers();
        } catch (ApiException e) {
            theLogger.error("pollMetadata failed due to: {}", e.getMessage(), e);
        }
    }

    @Override
    public void pollOdds() {
        try {
            processOddsMetadata();
        } catch (ApiException e) {
            theLogger.error("pollOdds failed due to: {}", e.getMessage(), e);
        }
    }

    private void processEventMetadata() throws ApiException {
        for (League myLeague : theConfig.theLeagues()) {
            Set<EventMetadata> myFetchedEventMetadata = theSportsApiClient.fetchEventMetadata(myLeague);
            Set<EventMetadata> myUniqueSet = theMetadataCache.getDifferentEventMetadata(myLeague, myFetchedEventMetadata);

            if (myUniqueSet.isEmpty()) {
                continue;
            }

            theMetadataCache.updateEventsMetadata(myLeague, myUniqueSet);
            theEventBus.publish(new EventMetadataUpdateEvent(myLeague, myUniqueSet));
        }
    }

    private void processMarketToBookmakers() throws ApiException {
        for (League myLeague : theConfig.theLeagues()) {
            Map<EventId, MarketToBookmakers> myFetchedMarketToBookmakers =
                    theSportsApiClient.fetchMarketToBookmakers(myLeague, theConfig.theRegions());

            for (Map.Entry<EventId, MarketToBookmakers> myEntry : myFetchedMarketToBookmakers.entrySet()) {
                EventId myEventId = myEntry.getKey();
                MarketToBookmakers myMarketToBookmakers = myEntry.getValue();

                MarketToBookmakers myUniqueSet = theMetadataCache.getDifferentMarketToBookmakers(myEventId, myMarketToBookmakers);

                if (myUniqueSet.theMarketToBookmakers().isEmpty()) {
                    continue;
                }

                MarketToBookmakers myFilteredSet = filterByMarkets(myUniqueSet);
                theMetadataCache.updateEventMarketToBookmakers(myEventId, myFilteredSet);
                theEventBus.publish(new MarketToBookmakersUpdateEvent(myLeague, myEventId, myFilteredSet));
            }
        }
    }

    private void processOddsMetadata() throws ApiException {
        for (League myLeague : theConfig.theLeagues()) {
            List<OddsMetadata> myFetchedOddsMetadataList = theSportsApiClient.fetchOddsMetadata(myLeague, theConfig.theMarkets(), theConfig.theRegions());

            for (OddsMetadata myFetchedOddsMetadata : myFetchedOddsMetadataList) {
                OddsMetadataId myOddId = myFetchedOddsMetadata.getId();
                boolean myFetchedMetadataExists = theOddsMetadataCache.containsKey(myOddId) &&
                        theOddsMetadataCache.get(myOddId).equals(myFetchedOddsMetadata);

                if (myFetchedMetadataExists) {
                    continue;
                }

                theOddsMetadataCache.put(myOddId, myFetchedOddsMetadata);
                theEventBus.publish(new OddsMetadataUpdateEvent(myFetchedOddsMetadata));
            }
        }
    }

    private MarketToBookmakers filterByMarkets(MarketToBookmakers aMarketToBookmakers) {
        Set<MarketType> myAllowedMarkets = theConfig.theMarkets();

        Map<MarketType, Set<Bookmaker>> filteredMap = aMarketToBookmakers.theMarketToBookmakers().entrySet().stream()
                .filter(entry -> myAllowedMarkets.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return new MarketToBookmakers(filteredMap);
    }
}