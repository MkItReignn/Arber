package com.arber.api.impl.theoddsapi;

import com.arber.datamodel.Bookmaker;
import com.arber.datamodel.MarketType;
import com.arber.api.datamodel.theoddsapi.TheOddsApiMarket;
import com.arber.api.datamodel.theoddsapi.TheOddsApiOdds;
import com.arber.utils.errors.ErrorHandler;

import java.util.*;
import java.util.stream.Collectors;

public final class TheOddsApiDataExtractor {
    private TheOddsApiDataExtractor() {}

    public static Set<MarketType> extractMarketTypes(TheOddsApiOdds[] anApiOdds, ErrorHandler anErrorHandler) {
        return Arrays.stream(anApiOdds)
                .flatMap(anOdds -> anOdds.getBookmakers().stream())
                .flatMap(aBookmaker -> aBookmaker.getMarkets().stream())
                .map(TheOddsApiMarket::getMarketKey)
                .map(anErrorHandler.wrapFunctionWithErrorHandling(TheOddsApiSchemaConverter::mapMarketKeyToMarketType))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static Set<Bookmaker> extractBookmakers(TheOddsApiOdds[] anApiOdds, MarketType aMarketType) {
        return Arrays.stream(anApiOdds)
                .flatMap(myOdds -> myOdds.getBookmakers().stream())
                .filter(bookmaker -> bookmaker.getMarkets()
                        .stream()
                        .anyMatch(market -> market.getMarketKey().equalsIgnoreCase(aMarketType.name())))
                .map(bookmaker -> Bookmaker.fromBookmakerKey(bookmaker.getBookmakerKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
