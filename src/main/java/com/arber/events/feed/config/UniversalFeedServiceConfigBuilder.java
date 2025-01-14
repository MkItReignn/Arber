package com.arber.events.feed.config;

import com.arber.datamodel.League;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;
import com.arber.datamodel.Sport;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class UniversalFeedServiceConfigBuilder {
    private Set<Sport> theSports = EnumSet.noneOf(Sport.class);
    private Set<League> theLeagues = EnumSet.noneOf(League.class);
    private Set<MarketType> theMarkets = EnumSet.noneOf(MarketType.class);
    private Set<Region> theRegions = EnumSet.noneOf(Region.class);

    public UniversalFeedServiceConfigBuilder setTheSports(Set<Sport> aSports) {
        if (aSports == null) {
            throw new IllegalArgumentException("Sports set cannot be null.");
        }
        theSports = EnumSet.copyOf(aSports);
        return this;
    }

    public UniversalFeedServiceConfigBuilder addSport(Sport aSport) {
        if (aSport == null) {
            throw new IllegalArgumentException("Sport cannot be null.");
        }
        theSports.add(aSport);
        return this;
    }

    public UniversalFeedServiceConfigBuilder setTheLeagues(Set<League> aLeagues) {
        if (aLeagues == null) {
            throw new IllegalArgumentException("Leagues set cannot be null.");
        }
        theLeagues = EnumSet.copyOf(aLeagues);
        return this;
    }

    public UniversalFeedServiceConfigBuilder addLeague(League aLeague) {
        if (aLeague == null) {
            throw new IllegalArgumentException("League cannot be null.");
        }
        theLeagues.add(aLeague);
        return this;
    }

    public UniversalFeedServiceConfigBuilder setTheMarkets(Set<MarketType> aMarkets) {
        if (aMarkets == null) {
            throw new IllegalArgumentException("Markets set cannot be null.");
        }
        theMarkets = EnumSet.copyOf(aMarkets);
        return this;
    }

    public UniversalFeedServiceConfigBuilder addMarket(MarketType aMarket) {
        if (aMarket == null) {
            throw new IllegalArgumentException("MarketType cannot be null.");
        }
        theMarkets.add(aMarket);
        return this;
    }

    public UniversalFeedServiceConfigBuilder setTheRegions(Set<Region> aRegions) {
        if (aRegions == null) {
            throw new IllegalArgumentException("Regions set cannot be null.");
        }
        theRegions = EnumSet.copyOf(aRegions);
        return this;
    }

    public UniversalFeedServiceConfigBuilder addRegion(Region aRegion) {
        if (aRegion == null) {
            throw new IllegalArgumentException("Region cannot be null.");
        }
        theRegions.add(aRegion);
        return this;
    }

    public UniversalFeedServiceConfig build() {
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

        Set<Sport> myImmutableSports = Collections.unmodifiableSet(EnumSet.copyOf(theSports));
        Set<League> myImmutableLeagues = Collections.unmodifiableSet(EnumSet.copyOf(theLeagues));
        Set<MarketType> myImmutableMarkets = Collections.unmodifiableSet(EnumSet.copyOf(theMarkets));
        Set<Region> myImmutableRegions = Collections.unmodifiableSet(EnumSet.copyOf(theRegions));

        return new UniversalFeedServiceConfig(
                myImmutableSports,
                myImmutableLeagues,
                myImmutableMarkets,
                myImmutableRegions
        );
    }
}