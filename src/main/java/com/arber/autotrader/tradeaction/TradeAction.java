package com.arber.autotrader.tradeaction;

import com.arber.datamodel.Bookmaker;
import com.arber.datamodel.EventId;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Participant;

public record TradeAction (
    EventId theEventId,
    Bookmaker theBookmaker,
    MarketType theMarketType,
    Participant theParticipant,
    double theStake,
    double thePrice)
{}