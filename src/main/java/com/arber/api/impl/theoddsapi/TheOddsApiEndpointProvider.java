package com.arber.api.impl.theoddsapi;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static String oddsEndpoint(LeagueKey aLeagueKey, Set<Region> aRegion) {
        return oddsEndpoint(aLeagueKey, aRegion, Map.of());
    }

    public static String oddsEndpoint(LeagueKey aLeagueKey, Set<Region> aRegion, Map<String, String> anOptionalParameters) {
        String myRegions = aRegion.stream()
                .map(region -> region.name().toLowerCase())
                .collect(Collectors.joining(","));
        anOptionalParameters.put("regions", myRegions);

        TheOddsApiEndpointParameterValidator.validateParameters(anOptionalParameters);

        String myEndpoint = ODDS_API_METADATA.theBaseUrl() +
                "/" +
                ODDS_API_METADATA.theApiVersion() +
                "/sports/" +
                aLeagueKey.theLeagueKey() +
                "/odds?" +
                "apiKey=" + ODDS_API_METADATA.theApiKey();
        return appendOptionalArguments(myEndpoint, anOptionalParameters);
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
}
