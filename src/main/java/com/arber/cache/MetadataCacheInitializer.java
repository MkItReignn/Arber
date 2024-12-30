package com.arber.cache;

import com.arber.api.SportsApiClient;
import com.arber.enums.League;
import com.arber.enums.Sport;
import com.arber.model.EventId;
import com.arber.model.EventMetadata;
import com.arber.model.LeagueMetadata;
import com.arber.model.MarketToBookmakers;

import java.util.Map;
import java.util.Set;

public class MetadataCacheInitializer {
    public static void initializeMetadataCache(MetadataCache aMetadataCache, SportsApiClient aSportsApiClient) {
        Set<Sport> mySports = aSportsApiClient.fetchAvailableSports();
        for (Sport mySport : mySports) {
            Set<LeagueMetadata> myLeagueMetadata = aSportsApiClient.fetchAvailableLeagueMetadata(mySport);
            aMetadataCache.insertLeagueMetadata(mySport, myLeagueMetadata);
        }

        Set<League> myLeagues = aSportsApiClient.fetchAvailableLeagues();
        for (League myLeague : myLeagues) {
            Set<EventMetadata> myEventMetadata = aSportsApiClient.fetchAvailableEvents(myLeague);
            aMetadataCache.insertEventsMetadata(myLeague, myEventMetadata);
        }

        for (League myLeague : myLeagues) {
            Map<EventId, MarketToBookmakers> myMarketToBookmakers = aSportsApiClient.fetchMarketToBookmakers(myLeague);

            for (Map.Entry<EventId, MarketToBookmakers> myEntry : myMarketToBookmakers.entrySet()) {
                aMetadataCache.insertMarketToBookmakers(myEntry.getKey(), myEntry.getValue());
            }
        }
    }
}
