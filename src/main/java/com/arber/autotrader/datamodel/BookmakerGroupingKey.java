package com.arber.autotrader.datamodel;

import com.arber.datamodel.EventId;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;

import java.util.Objects;

public record BookmakerGroupingKey(EventId theEventId, MarketType theMarketType, Region theRegion) {
    public BookmakerGroupingKey {
        Objects.requireNonNull(theEventId, "eventId must not be null");
        Objects.requireNonNull(theMarketType, "marketType must not be null");
        Objects.requireNonNull(theRegion, "region must not be null");
    }
}
