package com.arber.events.feed.config;

import com.arber.datamodel.EventId;
import com.arber.datamodel.EventMarketKey;
import com.arber.datamodel.League;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;
import com.arber.datamodel.Sport;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record SpecificFeedServiceConfig(
        Map<Sport, Set<League>> theSportToLeagues,
        Map<League, Set<EventId>> theLeagueToEvents,
        Map<EventId, Set<MarketType>> theEventToMarkets,
        Map<EventMarketKey, Set<Region>> theEventMarketToRegions)
        implements FeedServiceConfig {

    public SpecificFeedServiceConfig {
        Objects.requireNonNull(theSportToLeagues, "theSportToLeagues map cannot be null.");
        Objects.requireNonNull(theLeagueToEvents, "theLeagueToEvents map cannot be null.");
        Objects.requireNonNull(theEventToMarkets, "theEventToMarkets map cannot be null.");
        Objects.requireNonNull(theEventMarketToRegions, "theEventMarketToRegions map cannot be null.");

        theSportToLeagues.forEach((sport, leagues) -> {
            if (sport == null) {
                throw new IllegalArgumentException("Sport key cannot be null.");
            }
            if (leagues == null || leagues.isEmpty()) {
                throw new IllegalArgumentException("Leagues set for sport " + sport + " cannot be null or empty.");
            }
            leagues.forEach(league -> {
                if (league == null) {
                    throw new IllegalArgumentException("League in sport " + sport + " cannot be null.");
                }
            });
        });

        theLeagueToEvents.forEach((league, events) -> {
            if (league == null) {
                throw new IllegalArgumentException("League key cannot be null.");
            }
            if (events == null || events.isEmpty()) {
                throw new IllegalArgumentException("Events set for league " + league + " cannot be null or empty.");
            }
            events.forEach(eventId -> {
                if (eventId == null) {
                    throw new IllegalArgumentException("EventId in league " + league + " cannot be null.");
                }
            });
        });

        theEventToMarkets.forEach((eventId, markets) -> {
            if (eventId == null) {
                throw new IllegalArgumentException("EventId key cannot be null.");
            }
            if (markets == null || markets.isEmpty()) {
                throw new IllegalArgumentException("Markets set for event " + eventId + " cannot be null or empty.");
            }
            markets.forEach(marketType -> {
                if (marketType == null) {
                    throw new IllegalArgumentException("MarketType in event " + eventId + " cannot be null.");
                }
            });
        });

        theEventMarketToRegions.forEach((key, regions) -> {
            if (key == null) {
                throw new IllegalArgumentException("EventMarketKey cannot be null.");
            }
            if (regions == null || regions.isEmpty()) {
                throw new IllegalArgumentException("Regions set for EventMarketKey " + key + " cannot be null or empty.");
            }
            regions.forEach(region -> {
                if (region == null) {
                    throw new IllegalArgumentException("Region in EventMarketKey " + key + " cannot be null.");
                }
            });
        });

        crossValidateConsistency();
    }

    private void crossValidateConsistency() {
        theLeagueToEvents.keySet().forEach(league -> {
            boolean leagueExists = theSportToLeagues.values().stream()
                    .flatMap(Set::stream)
                    .anyMatch(existingLeague -> existingLeague.equals(league));
            if (!leagueExists) {
                throw new IllegalArgumentException("League " + league + " in theLeagueToEvents is not present in theSportToLeagues.");
            }
        });

        theEventToMarkets.keySet().forEach(eventId -> {
            boolean eventExists = theLeagueToEvents.values().stream()
                    .flatMap(Set::stream)
                    .anyMatch(existingEventId -> existingEventId.equals(eventId));
            if (!eventExists) {
                throw new IllegalArgumentException("EventId " + eventId + " in theEventToMarkets is not present in theLeagueToEvents.");
            }
        });

        theEventMarketToRegions.keySet().forEach(key -> {
            boolean marketExists = theEventToMarkets.getOrDefault(key.theEventId(), Set.of()).contains(key.theMarketType());
            if (!marketExists) {
                throw new IllegalArgumentException("MarketType " + key.theMarketType() + " for EventId " + key.theEventId() + " in theEventMarketToRegions is not present in theEventToMarkets.");
            }
        });
    }
}
