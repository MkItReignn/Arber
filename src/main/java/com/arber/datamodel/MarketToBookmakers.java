package com.arber.datamodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record MarketToBookmakers(Map<MarketType, Set<Bookmaker>> theMarketToBookmakers) {

    public static MarketToBookmakers combineMarketToBookmakers(MarketToBookmakers aMarketToBookmaker1,
                                                               MarketToBookmakers aMarketToBookmaker2) {
        Map<MarketType, Set<Bookmaker>> myCombinedMap = new HashMap<>(aMarketToBookmaker1.theMarketToBookmakers());

        aMarketToBookmaker2.theMarketToBookmakers().forEach((marketType, bookmakersSet) ->
                myCombinedMap.merge(
                        marketType,
                        new HashSet<>(bookmakersSet),
                        (existingSet, newSet) -> {
                            existingSet.addAll(newSet);
                            return existingSet;
                        }
                )
        );

        return new MarketToBookmakers(myCombinedMap);
    }
}