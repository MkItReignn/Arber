package com.arber.events.datamodel;

import com.arber.datamodel.EventId;
import com.arber.datamodel.MarketToBookmakers;

public record MarketToBookmakersUpdateEvent(EventId theEventId, MarketToBookmakers theMarketToBookmakers)
        implements SportsEvent {}
