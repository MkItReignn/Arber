package com.arber.api.impl.theoddsapi;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;
import com.arber.datamodel.LeagueKey;

public final class TheOddsApiEndpointProvider {
    private final static TheOddsApiMetadata ODDS_API_METADATA = TheOddsApiMetadataFactory.provideTheOddsApiVersionMetadata();

    private TheOddsApiEndpointProvider() {}

    private static String appendOptionalArguments(String aUrl, Map<String, String> anOptionalParameters) {
        if (anOptionalParameters.isEmpty()) {
            return aUrl;
        }

        TheOddsApiEndpointParameterValidator.validateParameters(anOptionalParameters);
        StringBuilder myUrlBuilder = new StringBuilder(aUrl);

        for (Map.Entry<String, String> myEntry : anOptionalParameters.entrySet()) {
            if (myEntry.getValue() != null && !myEntry.getValue().isEmpty()) {
                myUrlBuilder.append("&").append(myEntry.getKey()).append("=").append(myEntry.getValue());
            }
        }
        return myUrlBuilder.toString();
    }

    public static String sportsEndpoint() {
        return sportsEndpoint(Map.of());
    }

    public static String sportsEndpoint(Map<String, String> anOptionalParameters) {
        TheOddsApiEndpointParameterValidator.validateParameters(anOptionalParameters);
        String myEndpoint = ODDS_API_METADATA.theBaseUrl() +
                "/" +
                ODDS_API_METADATA.theApiVersion() +
                "/sports/?apiKey=" +
                ODDS_API_METADATA.theApiKey();
        return appendOptionalArguments(myEndpoint, anOptionalParameters);
    }

    public static String oddsEndpoint(LeagueKey aLeagueKey, Set<Region> aRegions) {
        return oddsEndpoint(aLeagueKey, aRegions, Set.of(MarketType.values()), Map.of());
    }

    public static String oddsEndpoint(LeagueKey aLeagueKey, Set<Region> aRegions, Set<MarketType> aMarketTypes) {
        return oddsEndpoint(aLeagueKey, aRegions, aMarketTypes, Map.of());
    }

    public static String oddsEndpoint(LeagueKey aLeagueKey, Set<Region> aRegion, Set<MarketType> aMarkets, Map<String, String> aParameters) {
        // TODO: An exception should occur where there NEEDS to be a region, if it is empty, we can't create an endpoint
        if (!aRegion.isEmpty()) {
            String myRegions = concatenateEnumNamesLowerCaseWithComma(aRegion);
            aParameters.put("regions", myRegions);
        }

        if (!aMarkets.isEmpty()) {
            String myMarkets = concatenateEnumNamesLowerCaseWithComma(aMarkets);
            aParameters.put("markets", myMarkets);
        }

        TheOddsApiEndpointParameterValidator.validateParameters(aParameters);

        String myEndpoint = ODDS_API_METADATA.theBaseUrl() +
                "/" +
                ODDS_API_METADATA.theApiVersion() +
                "/sports/" +
                aLeagueKey.theLeagueKey() +
                "/odds?" +
                "apiKey=" + ODDS_API_METADATA.theApiKey();
        return appendOptionalArguments(myEndpoint, aParameters);
    }

    public static String eventsEndpoint(LeagueKey aLeagueKey) {
        return eventsEndpoint(aLeagueKey, Map.of());
    }

    public static String eventsEndpoint(LeagueKey aLeagueKey, Map<String, String> anOptionalParameters) {
        TheOddsApiEndpointParameterValidator.validateParameters(anOptionalParameters);
        String myEndpoint = ODDS_API_METADATA.theBaseUrl() +
                "/" +
                ODDS_API_METADATA.theApiVersion() +
                "/sports/" +
                aLeagueKey.theLeagueKey() +
                "/events?apiKey=" +
                ODDS_API_METADATA.theApiKey();
        return appendOptionalArguments(myEndpoint, anOptionalParameters);
    }

    private static <E extends Enum<E>> String concatenateEnumNamesLowerCaseWithComma(Set<E> anEnums) {
        if (anEnums == null || anEnums.isEmpty()) {
            return "";
        }
        return anEnums.stream()
                .map(enumValue -> enumValue.name().toLowerCase())
                .collect(Collectors.joining(","));
    }
}
