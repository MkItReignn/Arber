package com.arber.autotrader.config;

import com.arber.datamodel.League;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;
import com.arber.datamodel.Sport;

import java.util.EnumSet;

public final class AgentConfig {
    private final EnumSet<Sport> theSports;
    private final EnumSet<League> theLeagues;
    private final EnumSet<MarketType> theMarkets;
    private final EnumSet<Region> theRegions;

    private AgentConfig(Builder aBuilder) {
        theSports = aBuilder.theSports;
        theLeagues = aBuilder.theLeagues;
        theMarkets = aBuilder.theMarkets;
        theRegions = aBuilder.theRegions;
    }

    public EnumSet<Sport> getTheSports() {
        return theSports;
    }

    public EnumSet<League> getTheLeagues() {
        return theLeagues;
    }

    public EnumSet<MarketType> getTheMarkets() {
        return theMarkets;
    }

    public EnumSet<Region> getTheRegions() {
        return theRegions;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private EnumSet<Sport> theSports = EnumSet.noneOf(Sport.class);
        private EnumSet<League> theLeagues = EnumSet.noneOf(League.class);
        private EnumSet<MarketType> theMarkets = EnumSet.noneOf(MarketType.class);
        private EnumSet<Region> theRegions = EnumSet.noneOf(Region.class);

        public Builder setSports(EnumSet<Sport> sports) { this.theSports = sports; return this; }
        public Builder setLeagues(EnumSet<League> leagues) { this.theLeagues = leagues; return this; }
        public Builder setMarkets(EnumSet<MarketType> markets) { this.theMarkets = markets; return this; }
        public Builder setRegions(EnumSet<Region> regions) { this.theRegions = regions; return this; }

        public AgentConfig build() {
            return new AgentConfig(this);
        }
    }
}
