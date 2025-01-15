package com.arber.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.arber.datamodel.*;

public class MetadataCache {
    private final ConcurrentMap<Sport, Set<LeagueMetadata>> theSportsMetadata;
    private final ConcurrentMap<League, Set<EventMetadata>> theEventsMetadata;
    private final ConcurrentMap<EventId, MarketToBookmakers> theEventMarketToBookmakers;

    public MetadataCache() {
        theSportsMetadata = new ConcurrentHashMap<>();
        theEventsMetadata = new ConcurrentHashMap<>();
        theEventMarketToBookmakers = new ConcurrentHashMap<>();
    }

    public Map<Sport, Set<LeagueMetadata>> getSportsMetadata() {
        return Collections.unmodifiableMap(theSportsMetadata);
    }

    public Map<League, Set<EventMetadata>> getEventsMetadata() {
        return Collections.unmodifiableMap(theEventsMetadata);
    }

    public Map<EventId, MarketToBookmakers> getEventMarketToBookmakers() {
        return Collections.unmodifiableMap(theEventMarketToBookmakers);
    }

    public void insertLeagueMetadata(Sport aSport,
                                     Set<LeagueMetadata> aLeagueMetadata) {
        theSportsMetadata.put(aSport, aLeagueMetadata);
    }

    public void insertEventsMetadata(League aLeague,
                                     Set<EventMetadata> anEventMetadata) {
        theEventsMetadata.put(aLeague, anEventMetadata);
    }

    public void insertMarketToBookmakers(EventId anEventId,
                                         MarketToBookmakers aMarketToBookmaker) {
        theEventMarketToBookmakers.put(anEventId, aMarketToBookmaker);
    }

    public void updateEventsMetadata(League aLeague, Set<EventMetadata> anEventMetadata) {
        theEventsMetadata.merge(aLeague, new HashSet<>(anEventMetadata), (existingSet, newSet) -> {
            existingSet.addAll(newSet);
            return existingSet;
        });
    }

    public void updateEventMarketToBookmakers(EventId anEventId, MarketToBookmakers aMarketToBookmaker) {
        theEventMarketToBookmakers.computeIfPresent(anEventId,
                (k, myCurrentMarketToBookmakers) -> MarketToBookmakers.combineMarketToBookmakers(myCurrentMarketToBookmakers, aMarketToBookmaker));
    }

    public Set<EventMetadata> getDifferentEventMetadata(League aLeague, Set<EventMetadata> anEventMetadata) {
        Set<EventMetadata> myCachedEventMetadata = theEventsMetadata.get(aLeague);

        if (myCachedEventMetadata == null) {
            return new HashSet<>(anEventMetadata);
        }

        Set<EventMetadata> myUniqueSet = new HashSet<>();

        for (EventMetadata myEventMetadata : anEventMetadata) {
            if (!myCachedEventMetadata.contains(myEventMetadata)) {
                myUniqueSet.add(myEventMetadata);
            }
        }

        return myUniqueSet;
    }

    public MarketToBookmakers getDifferentMarketToBookmakers(EventId anEventId, MarketToBookmakers aMarketToBookmaker) {
        MarketToBookmakers myCachedMarketToBookmakers = theEventMarketToBookmakers.get(anEventId);

        if (myCachedMarketToBookmakers == null) {
            return aMarketToBookmaker;
        }

        Map<MarketType, Set<Bookmaker>> myUniqueSet = new HashMap<>();

        for (Map.Entry<MarketType, Set<Bookmaker>> myEntry : aMarketToBookmaker.theMarketToBookmakers().entrySet()) {
            boolean myMarketTypeDoesNotExist = !myCachedMarketToBookmakers.theMarketToBookmakers().containsKey(myEntry.getKey());
            if (myMarketTypeDoesNotExist) {
                myUniqueSet.put(myEntry.getKey(), new HashSet<>(myEntry.getValue()));
            } else if (!myCachedMarketToBookmakers.theMarketToBookmakers().get(myEntry.getKey()).equals(myEntry.getValue())) {
                Set<Bookmaker> myNewBookmakers = getUniqueBookmakers(
                        myCachedMarketToBookmakers.theMarketToBookmakers().get(myEntry.getKey()),
                        myEntry.getValue());

                myUniqueSet.put(myEntry.getKey(), myNewBookmakers);
            }
        }

        return new MarketToBookmakers(myUniqueSet);
    }

    private static Set<Bookmaker> getUniqueBookmakers(Set<Bookmaker> aCurrentSet, Set<Bookmaker> aLatestSet) {
        return aLatestSet.stream()
                .filter(bookmaker -> !aCurrentSet.contains(bookmaker))
                .collect(Collectors.toSet());
    }

}
