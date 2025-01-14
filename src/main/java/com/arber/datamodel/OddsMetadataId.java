package com.arber.datamodel;

import java.util.Objects;

public record OddsMetadataId(
        EventId theEventId,
        MarketType theMarketType,
        Bookmaker theBookmaker
) {
    public OddsMetadataId {
        Objects.requireNonNull(theEventId, "eventId must not be null");
        Objects.requireNonNull(theMarketType, "marketType must not be null");
        Objects.requireNonNull(theBookmaker, "bookmaker must not be null");
    }
}