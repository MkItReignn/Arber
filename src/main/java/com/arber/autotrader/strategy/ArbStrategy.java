package com.arber.autotrader.strategy;

import com.arber.autotrader.datamodel.BookmakerGroupingKey;
import com.arber.autotrader.datamodel.Odds;
import com.arber.autotrader.tradeaction.TradeAction;
import com.arber.datamodel.Bookmaker;
import com.arber.events.datamodel.SportsEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArbStrategy implements TradingStrategy {

    @Override
    public Optional<List<TradeAction>> evaluate(SportsEvent anEvent, Map<BookmakerGroupingKey, Map<Bookmaker, Odds>> anOddsMetadataRegistry) {
        return Optional.empty();
    }
}
