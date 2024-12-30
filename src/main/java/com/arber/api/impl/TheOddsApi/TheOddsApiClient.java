package com.arber.api.impl;

import com.arber.api.SportsApiClient;
import com.arber.api.impl.TheOddsApi.TheOddsApiMetadataFactory;
import com.arber.enums.Bookmaker;
import com.arber.enums.League;
import com.arber.enums.MarketType;
import com.arber.enums.Sport;
import com.arber.model.EventMetadata;
import com.arber.model.Odds;

import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.arber.model.SportsMetadata;
import com.arber.model.theoddsapi.TheOddsApiSport;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TheOddsApiClient implements SportsApiClient {
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_VERSION = System.getenv("API_VERSION");
    private static final String BASE_URL = System.getenv("BASE_URL");
    private static final String API_NAME = System.getenv("API_NAME");
    // TODO: Add this to another class
    private final TheOddsApiEndpointProvider theOddsApiEndpointProvider;
    private String theSportsEndpoint;
    private String theOddsEndpoint;
    private String theScoresEndpoint;
    private String theEventsEndpoint;
    private String theEventOddsEndpoint;

    private final HttpClient theHttpClient;
    private final ObjectMapper theObjectMapper;

    // TODO: Add into factory constructor

    public TheOddsApiClient() {
        theVersionMetadata = TheOddsApiMetadataFactory.provideTheOddsApiVersionMetadata();
        theHttpClient = HttpClient.newHttpClient();
        theObjectMapper = new ObjectMapper();
        theOddsApiEndpointProvider = new TheOddsApiEndpointProvider();
    }


    @Override
    public Set<Sport> fetchAvailableSports() {
        try {
            Set<Sport> mySetOfSports = new HashSet<>();
            final String myEndPoint = BASE_URL + "/" + API_VERSION + "/" + "sports?apiKey=" + API_KEY;

            TheOddsApiSport[] myOddsApiSports = executeGet(myEndPoint, TheOddsApiSport[].class);

            for (TheOddsApiSport myOddsApiSport : myOddsApiSports ) {
                Sport mySport = mapGroupToSport(myOddsApiSport.theGroup);

                if (mySport != null) {
                    mySetOfSports.add(mySport);
                } else {
                    // TODO: Use SL4J with logback - ChatGPT
                    System.err.println("Warning: Unrecognized sport group - " + myOddsApiSport.theGroup);
                }
            }

            return mySetOfSports;
        } catch (Exception myException) {
            throw new RuntimeException("Failed to fetch available sports", myException);
        }
    }

    // TODO: Put this in another class TheOddsSchemaConverter
    private Sport mapGroupToSport(String aGroup) {
        if (aGroup == null) {
            return null;
        }

        return switch (aGroup.toLowerCase()) {
            case "soccer" -> Sport.SOCCER;
            case "basketball" -> Sport.BASKETBALL;
            case "american football" -> Sport.AMERICAN_FOOTBALL;
            case "tennis" -> Sport.TENNIS;
            case "horse racing" -> Sport.HORSE_RACING;
            case "cricket" -> Sport.CRICKET;
            case "baseball" -> Sport.BASEBALL;
            case "mixed martial arts" -> Sport.MMA;
            case "ice hockey" -> Sport.ICE_HOCKEY;
            case "rugby league" -> Sport.RUGBY;
            case "aussie rules" -> Sport.AUSTRALIAN_FOOTBALL;
            case "golf" -> Sport.GOLF;
            case "boxing" -> Sport.BOXING;
            default -> null; // Return null for unmatched group names
        };
    }

    @Override
    public Set<League> fetchAvailableLeagues() {
        Set<League> allLeagues = new HashSet<>();
        for (Sport aSport : fetchAvailableSports()) {
            allLeagues.addAll(fetchAvailableLeagues(aSport));
        }
        return allLeagues;
    }

    // TODO: Move this as well
    private League mapTitleToLeague(String aTitle) {
        if (aTitle == null) {
            return null;
        }

        String myNormalizedTitle = aTitle.toLowerCase();

        return switch (myNormalizedTitle) {
            // American Football
            case "ncaaf" -> League.NCAAF;
            case "ncaaf championship winner" -> League.NCAAF;
            case "nfl" -> League.NFL;
            case "nfl super bowl winner" -> League.NFL_SUPER_BOWL;

            // Aussie Rules
            case "afl" -> League.AFL;

            // Baseball
            case "mlb world series winner" -> League.MLB;

            // Basketball
            case "nba" -> League.NBA;
            case "nba championship winner" -> League.NBA;
            case "basketball euroleague" -> League.EUROLEAGUE;
            case "nbl" -> League.NBL;
            case "ncaab", "ncaab championship winner" -> League.NCAAB;

            // Boxing
            case "boxing" -> League.BOXING;

            // Cricket
            case "big bash" -> League.BIG_BASH;
            case "test matches" -> League.TEST_MATCHES;

            // Golf
            case "masters tournament winner" -> League.MASTERS;
            case "pga championship winner" -> League.PGA_CHAMPIONSHIP;
            case "the open winner" -> League.THE_OPEN_CHAMPIONSHIP;
            case "us open winner" -> League.US_OPEN_GOLF;

            // Ice Hockey
            case "nhl" -> League.NHL;
            case "nhl championship winner" -> League.NHL;
            case "hockeyallsvenskan" -> League.HOCKEY_ALLSVENSKAN;
            case "shl" -> League.SHL;

            // MMA
            case "mma" -> League.MMA;

            // Rugby
            case "nrl" -> League.NRL;

            // Soccer
            case "a-league" -> League.A_LEAGUE;
            case "austrian football bundesliga" -> League.AUSTRIAN_BUNDESLIGA;
            case "belgium first div" -> League.BELGIUM_FIRST_DIV;
            case "championship" -> League.EFL_CHAMPIONSHIP;
            case "efl cup" -> League.EFL_CUP;
            case "league 1" -> League.EFL_LEAGUE_ONE;
            case "league 2" -> League.EFL_LEAGUE_TWO;
            case "epl" -> League.PREMIER_LEAGUE;
            case "fa cup" -> League.FA_CUP;
            case "fifa world cup winner" -> League.FIFA_WORLD_CUP;
            case "ligue 1 - france" -> League.LIGUE_ONE;
            case "ligue 2 - france" -> League.LIGUE_TWO;
            case "bundesliga - germany" -> League.BUNDESLIGA;
            case "bundesliga 2 - germany" -> League.BUNDESLIGA_TWO;
            case "super league - greece" -> League.SUPER_LEAGUE_GREECE;
            case "serie a - italy" -> League.SERIE_A;
            case "serie b - italy" -> League.SERIE_B;
            case "league of ireland" -> League.LEAGUE_OF_IRELAND;
            case "liga mx" -> League.LIGA_MX;
            case "dutch eredivisie" -> League.DUTCH_EREDEVISIE;
            case "primeira liga - portugal" -> League.PRIMEIRA_LIGA;
            case "la liga - spain" -> League.LA_LIGA;
            case "la liga 2 - spain" -> League.LA_LIGA_TWO;
            case "premiership - scotland" -> League.SCOTTISH_PREMIERSHIP;
            case "allsvenskan - sweden" -> League.ALLSVENSKAN;
            case "swiss superleague" -> League.SWISS_SUPER_LEAGUE;
            case "uefa champions league" -> League.UEFA_CHAMPIONS_LEAGUE;
            case "uefa europa league" -> League.UEFA_EUROPA_LEAGUE;
            default -> null; // Return null for unmatched group names
        };
    }

    @Override
    public Set<League> fetchAvailableLeagues(Sport aSport) {
        try {
            Set<League> mySetOfLeagues = new HashSet<>();
            final String myEndPoint = BASE_URL + "/" + API_VERSION + "/" + "sports?apiKey=" + API_KEY;

            TheOddsApiSport[] myOddsApiSports = executeGet(myEndPoint, TheOddsApiSport[].class);

            for (TheOddsApiSport myOddsApiSport : myOddsApiSports) {
                if (mapGroupToSport(myOddsApiSport.theGroup) == aSport) {
                    League myLeague = mapTitleToLeague(myOddsApiSport.theTitle);
                    if (myLeague != null) {
                        mySetOfLeagues.add(myLeague);
                    } else {
                        System.err.println("Warning: Unrecognized league title - " + myOddsApiSport.theTitle);
                    }
                }
            }

            return mySetOfLeagues;
        } catch (Exception myException) {
            throw new RuntimeException("Failed to fetch available leagues for sport: " + aSport, myException);
        }
    }

    @Override
    public Set<EventMetadata> fetchAvailableEvents() {

        // GET /v4/sports/{sport}/events?apiKey={apiKey}
        // You need to map your League to a TheOddsApi sport key
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Set<EventMetadata> fetchAvailableEvents(SportsMetadata aSportsMetadata) {
        // GET /v4/sports/{sport}/events?apiKey={apiKey}
        // You need to map your League to a TheOddsApi sport key
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<MarketType> fetchMarketTypes(String anEventId) {
        // TheOddsApi doesn’t have a direct "fetch markets by eventId" endpoint.
        // You’d likely call /events/{eventId}/odds or /odds and parse the returned "markets" fields from bookmakers
        // Then extract unique market keys.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Bookmaker> fetchBookmakerTypes(String anEventId) {
        // Similar logic: Call event odds or odds endpoint, parse bookmakers, and return the corresponding BookmakerTypes.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Odds> fetchOdds(String anEventId) {
        // You might need to call GET /v4/sports/{sport}/events/{eventId}/odds or /odds by sport and filter by eventId.
        // This requires that you already know which sport the event belongs to.
        // If not known, you might store sport-event mappings from previous fetches.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Odds> fetchOdds(String anEventId, Bookmaker aBookmaker) {
        // Similar to fetchOdds(String), but filter by a specific bookmaker key in the response.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isApiAvailable() {
        // Could do a simple health check by calling /v4/sports?apiKey and see if it returns 200
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

    private <T> T executeGet(String anEndpoint, Class<T> aResponseType) throws Exception {
        System.out.println(anEndpoint);
        HttpRequest myRequest = HttpRequest.newBuilder(URI.create(anEndpoint)).GET().build();
        HttpResponse<String> myResponse = theHttpClient.send(myRequest, HttpResponse.BodyHandlers.ofString());

        if (myResponse.statusCode() == 200) {
            return theObjectMapper.readValue(myResponse.body(), aResponseType);
        } else {
            // Handle errors (log, throw exception, etc.)
            throw new RuntimeException("API call failed with status: " + myResponse.statusCode() + " Body: " + myResponse.body());
        }
    }
}
