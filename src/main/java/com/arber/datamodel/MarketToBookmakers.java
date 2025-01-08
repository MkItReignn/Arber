package com.arber.datamodel;

import java.util.Map;
import java.util.Set;

public record MarketToBookmakers(Map<MarketType, Set<Bookmaker>> theMarketToBookmakers) {}