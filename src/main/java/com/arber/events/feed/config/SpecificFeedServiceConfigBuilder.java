package com.arber.events.feed.config;

import com.arber.datamodel.EventId;
import com.arber.datamodel.EventMarketKey;
import com.arber.datamodel.League;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;
import com.arber.datamodel.Sport;

import java.util.*;

public class SpecificFeedServiceConfigBuilder {
    private final Map<Sport, Set<League>> theSportToLeagues = new EnumMap<>(Sport.class);
    private final Map<League, Set<EventId>> theLeagueToEvents = new EnumMap<>(League.class);
    private final Map<EventId, Set<MarketType>> theEventToMarkets = new HashMap<>();
    private final Map<EventMarketKey, Set<Region>> theEventMarketToRegions = new HashMap<>();

    public SpecificFeedServiceConfigBuilder setSportToLeagues(Map<Sport, Set<League>> aSportToLeagues) {
        if (aSportToLeagues == null) {
            throw new IllegalArgumentException("sportToLeagues map cannot be null.");
        }
        theSportToLeagues.clear();
        aSportToLeagues.forEach((aSport, aLeagues) -> {
            validateNotNull(aSport, "Sport key in sportToLeagues cannot be null.");
            validateSet(aLeagues, "Leagues set for sport " + aSport + " cannot be null or empty.");
            theSportToLeagues.put(aSport, new HashSet<>(aLeagues));
        });
        return this;
    }

    public SpecificFeedServiceConfigBuilder addSportToLeagues(Sport aSport, Set<League> aLeagues) {
        validateNotNull(aSport, "Sport cannot be null.");
        validateSet(aLeagues, "Leagues set for sport " + aSport + " cannot be null or empty.");
        theSportToLeagues.computeIfAbsent(aSport, k -> new HashSet<>()).addAll(aLeagues);
        return this;
    }

    public SpecificFeedServiceConfigBuilder setLeagueToEvents(Map<League, Set<EventId>> aLeagueToEvents) {
        if (aLeagueToEvents == null) {
            throw new IllegalArgumentException("leagueToEvents map cannot be null.");
        }
        theLeagueToEvents.clear();
        aLeagueToEvents.forEach((aLeague, aEvents) -> {
            validateNotNull(aLeague, "League key in leagueToEvents cannot be null.");
            validateSet(aEvents, "Events set for league " + aLeague + " cannot be null or empty.");
            theLeagueToEvents.put(aLeague, new HashSet<>(aEvents));
        });
        return this;
    }

    public SpecificFeedServiceConfigBuilder addLeagueToEvents(League aLeague, Set<EventId> aEventIds) {
        validateNotNull(aLeague, "League cannot be null.");
        validateSet(aEventIds, "Events set for league " + aLeague + " cannot be null or empty.");
        theLeagueToEvents.computeIfAbsent(aLeague, k -> new HashSet<>()).addAll(aEventIds);
        return this;
    }

    public SpecificFeedServiceConfigBuilder setEventToMarkets(Map<EventId, Set<MarketType>> aEventToMarkets) {
        if (aEventToMarkets == null) {
            throw new IllegalArgumentException("eventToMarkets map cannot be null.");
        }
        theEventToMarkets.clear();
        aEventToMarkets.forEach((aEventId, aMarkets) -> {
            validateNotNull(aEventId, "EventId key in eventToMarkets cannot be null.");
            validateSet(aMarkets, "Markets set for event " + aEventId + " cannot be null or empty.");
            theEventToMarkets.put(aEventId, new HashSet<>(aMarkets));
        });
        return this;
    }

    public SpecificFeedServiceConfigBuilder addEventToMarkets(EventId aEventId, Set<MarketType> aMarketTypes) {
        validateNotNull(aEventId, "EventId cannot be null.");
        validateSet(aMarketTypes, "Markets set for event " + aEventId + " cannot be null or empty.");
        theEventToMarkets.computeIfAbsent(aEventId, k -> new HashSet<>()).addAll(aMarketTypes);
        return this;
    }

    public SpecificFeedServiceConfigBuilder setEventMarketToRegions(Map<EventMarketKey, Set<Region>> aEventMarketToRegions) {
        if (aEventMarketToRegions == null) {
            throw new IllegalArgumentException("eventMarketToRegions map cannot be null.");
        }
        theEventMarketToRegions.clear();
        aEventMarketToRegions.forEach((aKey, aRegions) -> {
            validateNotNull(aKey, "EventMarketKey in eventMarketToRegions cannot be null.");
            validateSet(aRegions, "Regions set for EventMarketKey " + aKey + " cannot be null or empty.");
            theEventMarketToRegions.put(aKey, new HashSet<>(aRegions));
        });
        return this;
    }

    public SpecificFeedServiceConfigBuilder addEventMarketToRegions(EventMarketKey aKey, Set<Region> aRegions) {
        validateNotNull(aKey, "EventMarketKey cannot be null.");
        validateSet(aRegions, "Regions set for EventMarketKey " + aKey + " cannot be null or empty.");
        theEventMarketToRegions.computeIfAbsent(aKey, k -> new HashSet<>()).addAll(aRegions);
        return this;
    }

    public SpecificFeedServiceConfig build() {
        Map<Sport, Set<League>> myImmutableSportToLeagues = deepImmutableMap(theSportToLeagues);
        Map<League, Set<EventId>> myImmutableLeagueToEvents = deepImmutableMap(theLeagueToEvents);
        Map<EventId, Set<MarketType>> myImmutableEventToMarkets = deepImmutableMap(theEventToMarkets);
        Map<EventMarketKey, Set<Region>> myImmutableEventMarketToRegions = deepImmutableMap(theEventMarketToRegions);

        return new SpecificFeedServiceConfig(
                myImmutableSportToLeagues,
                myImmutableLeagueToEvents,
                myImmutableEventToMarkets,
                myImmutableEventMarketToRegions
        );
    }

    private void validateNotNull(Object anObject, String aMessage) {
        if (anObject == null) {
            throw new IllegalArgumentException(aMessage);
        }
    }

    private <T> void validateSet(Set<T> aSet, String aMessage) {
        if (aSet == null || aSet.isEmpty()) {
            throw new IllegalArgumentException(aMessage);
        }
        if (aSet.contains(null)) {
            throw new IllegalArgumentException(aMessage + " It contains null elements.");
        }
    }

    private <K, V> Map<K, Set<V>> deepImmutableMap(Map<K, Set<V>> aOriginalMap) {
        Map<K, Set<V>> myCopy = new HashMap<>();
        aOriginalMap.forEach((aK, aV) -> myCopy.put(aK, Collections.unmodifiableSet(new HashSet<>(aV))));
        return Collections.unmodifiableMap(myCopy);
    }
}