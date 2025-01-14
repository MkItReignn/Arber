package com.arber.datamodel;

import java.util.Objects;

public record EventMarketKey(EventId theEventId, MarketType theMarketType) {

    public EventMarketKey {
        Objects.requireNonNull(theEventId, "EventId cannot be null.");
        Objects.requireNonNull(theMarketType, "MarketType cannot be null.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventMarketKey that)) return false;
        return theEventId.equals(that.theEventId()) && theMarketType.equals(that.theMarketType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(theEventId, theMarketType);
    }
}
