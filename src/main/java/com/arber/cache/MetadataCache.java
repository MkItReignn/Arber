package com.arber.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.arber.datamodel.*;

public class MetadataCache {
    private final Map<Sport, Set<LeagueMetadata>> theSportsMetadata;
    private final Map<League, Set<EventMetadata>> theEventsMetadata;
    private final Map<EventId, MarketToBookmakers> theEventMarketToBookmakers;

    public MetadataCache() {
        theSportsMetadata = new HashMap<>();
        theEventsMetadata = new HashMap<>();
        theEventMarketToBookmakers = new HashMap<>();
    }

    public void insertMarketToBookmakers(EventId anEventId,
                                         MarketToBookmakers aMarketToBookmaker) {
        theEventMarketToBookmakers.put(anEventId, aMarketToBookmaker);
    }

    public void insertLeagueMetadata(Sport aSetOfSports,
                                     Set<LeagueMetadata> aLeagueMetadatas) {
        theSportsMetadata.put(aSetOfSports, aLeagueMetadatas);
    }

    public void insertEventsMetadata(League aLeague,
                                     Set<EventMetadata> aEventMetadtas) {
        theEventsMetadata.put(aLeague, aEventMetadtas);
    }
}
