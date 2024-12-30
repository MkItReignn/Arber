package com.arber.model;

import com.arber.enums.Bookmaker;
import com.arber.enums.MarketType;

import java.util.Map;
import java.util.Set;

public record MarketToBookmakers(Map<MarketType, Set<Bookmaker>> theMarketToBookmakers) {
}
