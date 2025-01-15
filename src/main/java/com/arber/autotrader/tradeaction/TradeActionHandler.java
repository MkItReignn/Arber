package com.arber.autotrader.tradeaction;

import java.util.List;

public interface TradeActionHandler {
    void handle(List<TradeAction> aTradeActions);
}
