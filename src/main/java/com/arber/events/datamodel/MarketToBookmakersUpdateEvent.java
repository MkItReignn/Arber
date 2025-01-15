package com.arber.events.datamodel;

import com.arber.datamodel.EventId;
import com.arber.datamodel.League;
import com.arber.datamodel.MarketToBookmakers;

public record MarketToBookmakersUpdateEvent(League theLeague, EventId theEventId, MarketToBookmakers theMarketToBookmakers)
        implements SportsEvent {}
