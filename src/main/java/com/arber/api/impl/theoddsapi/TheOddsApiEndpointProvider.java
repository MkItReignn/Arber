package com.arber.api.impl.TheOddsApi;

import java.util.Map;
import com.arber.enums.Region;

// NOTE: Should this be all static methods?
public class TheOddsApiEndpointProvider {
    private final TheOddsApiMetadata theVersionMetadata;

    public TheOddsApiEndpointProvider() {
        theVersionMetadata = TheOddsApiMetadataFactory.provideTheOddsApiVersionMetadata();
    }

    // Helper to append optional arguments to the URL
    // TODO: anOptionalArguments should have a check on what arguments are permitted for each endpoint
    private String appendOptionalArguments(String aUrl, Map<String, String> anOptionalArguments) {
        if (anOptionalArguments == null || anOptionalArguments.isEmpty()) {
            return aUrl;
        }

        StringBuilder myUrlBuilder = new StringBuilder(aUrl);
        for (Map.Entry<String, String> myEntry : anOptionalArguments.entrySet()) {
            if (myEntry.getValue() != null && !myEntry.getValue().isEmpty()) {
                myUrlBuilder.append("&").append(myEntry.getKey()).append("=").append(myEntry.getValue());
            }
        }
        return myUrlBuilder.toString();
    }

    // Overloaded Sports endpoint
    public String sportsEndpoint() {
        return sportsEndpoint(null);
    }

    public String sportsEndpoint(Map<String, String> anOptionalArguments) {
        String myUrl = theVersionMetadata.theBaseUrl() + "/" + theVersionMetadata.theApiVersion() + "/sports/?apiKey=" + theVersionMetadata.theApiKey();
        return appendOptionalArguments(myUrl, anOptionalArguments);
    }

    // Overloaded Odds endpoint
    public String oddsEndpoint(String aLeagueKey, Region aRegion) {
        return oddsEndpoint(aLeagueKey, aRegion, null);
    }

    public String oddsEndpoint(String aSportKey, Region aRegion, Map<String, String> anOptionalArguments) {
        if (aSportKey == null || aSportKey.isEmpty()) {
            throw new IllegalArgumentException("Sport key is required for the odds endpoint.");
        }
        if (aRegion == null) {
            throw new IllegalArgumentException("Region is required for the odds endpoint.");
        }

        StringBuilder myUrlBuilder = new StringBuilder();
        myUrlBuilder.append(theVersionMetadata.theBaseUrl())
                .append("/")
                .append(theVersionMetadata.theApiVersion())
                .append("/sports/")
                .append(aSportKey)
                .append("/odds?")
                .append("apiKey=").append(theVersionMetadata.theApiKey())
                .append("&regions=").append(aRegion.name().toLowerCase());

        return appendOptionalArguments(myUrlBuilder.toString(), anOptionalArguments);
    }


    // Overloaded Events endpoint
    public String eventsEndpoint(String aLeagueKey) {
        return eventsEndpoint(aLeagueKey, null);
    }

    public String eventsEndpoint(String aLeagueKey, Map<String, String> anOptionalArguments) {
        String myUrl = theVersionMetadata.theBaseUrl() + "/" + theVersionMetadata.theApiVersion() + "/sports/"
                + aLeagueKey + "/events?apiKey=" + theVersionMetadata.theApiKey();
        return appendOptionalArguments(myUrl, anOptionalArguments);
    }

    // Overloaded Event-specific odds endpoint
    public String eventOddsEndpoint(String aLeagueKey, String anEventId) {
        return eventOddsEndpoint(aLeagueKey, anEventId, null);
    }

    public String eventOddsEndpoint(String aLeagueKey, String anEventId, Map<String, String> anOptionalArguments) {
        String myUrl = theVersionMetadata.theBaseUrl() + "/" + theVersionMetadata.theApiVersion() + "/sports/"
                + aLeagueKey + "/events/" + anEventId + "/odds?apiKey=" + theVersionMetadata.theApiKey();
        return appendOptionalArguments(myUrl, anOptionalArguments);
    }

    // Overloaded Scores endpoint
    public String scoresEndpoint(String aLeagueKey) {
        return scoresEndpoint(aLeagueKey, null);
    }

    public String scoresEndpoint(String aLeagueKey, Map<String, String> anOptionalArguments) {
        String myUrl = theVersionMetadata.theBaseUrl() + "/" + theVersionMetadata.theApiVersion() + "/sports/"
                + aLeagueKey + "/scores/?apiKey=" + theVersionMetadata.theApiKey();
        return appendOptionalArguments(myUrl, anOptionalArguments);
    }

    // Overloaded Historical odds endpoint
    public String historicalOddsEndpoint(String aLeagueKey, String aDate) {
        return historicalOddsEndpoint(aLeagueKey, aDate, null);
    }

    // TODO: This needs to be re-written I am pretty sure it is wrong.
    // TODO: Also missing historical events
    public String historicalOddsEndpoint(String aLeagueKey, String aDate, Map<String, String> anOptionalArguments) {
        String myUrl = theVersionMetadata.theBaseUrl() + "/" + theVersionMetadata.theApiVersion() + "/odds-history/"
                + aLeagueKey + "?date=" + aDate + "&apiKey=" + theVersionMetadata.theApiKey();
        return appendOptionalArguments(myUrl, anOptionalArguments);
    }
}
