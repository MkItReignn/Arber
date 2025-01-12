package com.arber.cache;

import com.arber.api.SportsApiClient;
import com.arber.api.exception.ApiException;
import com.arber.api.exception.MetadataCacheInitializerException;
import com.arber.datamodel.League;
import com.arber.datamodel.Sport;
import com.arber.datamodel.EventId;
import com.arber.datamodel.EventMetadata;
import com.arber.datamodel.LeagueMetadata;
import com.arber.datamodel.MarketToBookmakers;

import java.util.Map;
import java.util.Set;

public final class MetadataCacheInitializer {
    public static void initializeMetadataCache(MetadataCache aMetadataCache, SportsApiClient aSportsApiClient) throws MetadataCacheInitializerException {
        Set<Sport> mySports = null;
        try {
            mySports = aSportsApiClient.fetchAvailableSports();
        } catch (ApiException e) {
            throw new MetadataCacheInitializerException(
                    "Unable to initialize MetadataCache, Failed to fetch available sports.", e);
        }

        for (Sport mySport : mySports) {
            try {
                Set<LeagueMetadata> myLeagueMetadata = aSportsApiClient.fetchLeagueMetadata(mySport);
                aMetadataCache.insertLeagueMetadata(mySport, myLeagueMetadata);
            } catch (ApiException e) {
                throw new MetadataCacheInitializerException(
                        String.format("Unable to initialize MetadataCache, Failed to fetch League Metadata for '%s'", mySport.toString()), e);
            }
        }

        Set<League> myLeagues = null;
        try {
            myLeagues = aSportsApiClient.fetchAvailableLeagues();
        } catch (ApiException e) {
            throw new MetadataCacheInitializerException(
                    "Unable to initialize MetadataCache, Failed to fetch available leagues.", e);
        }

        for (League myLeague : myLeagues) {
            try {
                Set<EventMetadata> myEventMetadata = aSportsApiClient.fetchEventMetadata(myLeague);
                aMetadataCache.insertEventsMetadata(myLeague, myEventMetadata);
            } catch (ApiException e) {
                throw new MetadataCacheInitializerException(
                        String.format("Unable to initialize MetadataCache, Failed to fetch EventMetadata for '%s'", myLeague.toString()), e
                );
            }
        }

        for (League myLeague : myLeagues) {
            Map<EventId, MarketToBookmakers> myMarketToBookmakers = null;
            try {
                myMarketToBookmakers = aSportsApiClient.fetchMarketToBookmakers(myLeague);
                for (Map.Entry<EventId, MarketToBookmakers> myEntry : myMarketToBookmakers.entrySet()) {
                    aMetadataCache.insertMarketToBookmakers(myEntry.getKey(), myEntry.getValue());
                }
            } catch (ApiException e) {
                throw new MetadataCacheInitializerException(
                        String.format("Unable to initialize MetadataCache, Failed to fetch MarketToBookmakers for '%s'", myLeague.toString()), e
                );
            }

        }
    }
}
