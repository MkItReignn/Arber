package com.arber.events.feed.config;

import com.arber.datamodel.League;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;
import com.arber.datamodel.Sport;

import java.util.Set;

public record UniversalFeedServiceConfig(Set<Sport> theSports, Set<League> theLeagues, Set<MarketType> theMarkets,
                                         Set<Region> theRegions) implements FeedServiceConfig {
    public UniversalFeedServiceConfig {
        if (theSports == null || theSports.isEmpty()) {
            throw new IllegalArgumentException("Sports set cannot be null or empty.");
        }
        if (theLeagues == null || theLeagues.isEmpty()) {
            throw new IllegalArgumentException("Leagues set cannot be null or empty.");
        }
        if (theMarkets == null || theMarkets.isEmpty()) {
            throw new IllegalArgumentException("Markets set cannot be null or empty.");
        }
        if (theRegions == null || theRegions.isEmpty()) {
            throw new IllegalArgumentException("Regions set cannot be null or empty.");
        }
    }
}