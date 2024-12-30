package com.arber.api.impl.TheOddsApi;

import com.arber.api.SportsApiClient;
import com.arber.enums.*;
import com.arber.model.*;

import java.awt.print.Book;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.arber.model.TheOddsApi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TheOddsApiClient implements SportsApiClient {
    private final Logger theLogger = LoggerFactory.getLogger(TheOddsApiClient.class);

    private final TheOddsApiEndpointProvider theOddsApiEndpointProvider;
    private final HttpClient theHttpClient;
    private final ObjectMapper theObjectMapper;
    // NOTE: Maybe create a class within this class of this cache.
    private final Map<String, String> theEventIdToLeagueKeyCache;
    private final Map<League, String> theLeagueToKeyCache;


    public TheOddsApiClient() {
        theOddsApiEndpointProvider = new TheOddsApiEndpointProvider();
        theHttpClient = HttpClient.newHttpClient();
        theObjectMapper = new ObjectMapper();
        theEventIdToLeagueKeyCache = new ConcurrentHashMap<>();
        theLeagueToKeyCache = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Sport> fetchAvailableSports() {
        try {
            Set<Sport> mySetOfSports = new HashSet<>();
            final String myEndPoint = theOddsApiEndpointProvider.sportsEndpoint();

            TheOddsApiSport[] myOddsApiSports = executeGet(myEndPoint, TheOddsApiSport[].class);

            for (TheOddsApiSport myOddsApiSport : myOddsApiSports ) {
                Sport mySport = TheOddsSchemaConverter.mapGroupToSport(myOddsApiSport.getTheGroup());

                if (mySport != null) {
                    mySetOfSports.add(mySport);
                } else {
                    theLogger.warn("Warning: Unrecognized sport group - {}", myOddsApiSport.getTheGroup());
                }
            }

            return mySetOfSports;
        } catch (Exception myException) {
            throw new RuntimeException("Failed to fetch available sports", myException);
        }
    }

    @Override
    public Set<League> fetchAvailableLeagues() {
        Set<League> allLeagues = new HashSet<>();
        for (Sport aSport : fetchAvailableSports()) {
            allLeagues.addAll(fetchAvailableLeagues(aSport));
        }
        return allLeagues;
    }

    @Override
    public Set<League> fetchAvailableLeagues(Sport aSport) {
        try {
            Set<League> mySetOfLeagues = new HashSet<>();
            final String myEndPoint = theOddsApiEndpointProvider.sportsEndpoint();

            TheOddsApiSport[] myOddsApiSports = executeGet(myEndPoint, TheOddsApiSport[].class);

            for (TheOddsApiSport myOddsApiSport : myOddsApiSports) {
                if (TheOddsSchemaConverter.mapGroupToSport(myOddsApiSport.getTheGroup()) == aSport) {
                    League myLeague = TheOddsSchemaConverter.mapTitleToLeague(myOddsApiSport.getTheTitle());
                    if (myLeague != null) {
                        mySetOfLeagues.add(myLeague);
                    } else {
                        theLogger.warn("Warning: Unrecognized league title - {}", myOddsApiSport.getTheTitle());
                    }
                }
            }

            return mySetOfLeagues;
        } catch (Exception myException) {
            throw new RuntimeException("Failed to fetch available leagues for sport: " + aSport, myException);
        }
    }

    @Override
    public Set<LeagueMetadata> fetchAvailableLeagueMetadata(Sport aSport) {
        try {
            Set<LeagueMetadata> myLeagueMetadataSet = new HashSet<>();
            final String myEndPoint = theOddsApiEndpointProvider.sportsEndpoint();

            TheOddsApiSport[] myOddsApiSports = executeGet(myEndPoint, TheOddsApiSport[].class);

            for (TheOddsApiSport myOddsApiSport : myOddsApiSports) {
                if (TheOddsSchemaConverter.mapGroupToSport(myOddsApiSport.getTheGroup()) == aSport) {
                    League myLeague = TheOddsSchemaConverter.mapTitleToLeague(myOddsApiSport.getTheTitle());

                    if (myLeague != null) {
                        // Construct LeagueMetadata object
                        LeagueMetadata myLeagueMetadata = new LeagueMetadata(
                                aSport,
                                myLeague,
                                myOddsApiSport.getTheKey(),
                                myOddsApiSport.getTheTitle(),
                                Optional.ofNullable(myOddsApiSport.getTheDescription())
                        );

                        myLeagueMetadataSet.add(myLeagueMetadata);
                    } else {
                        theLogger.warn("Warning: Unrecognized league title - {}", myOddsApiSport.getTheTitle());
                    }
                }
            }

            return myLeagueMetadataSet;
        } catch (Exception myException) {
            throw new RuntimeException("Failed to fetch league metadata for sport: " + aSport, myException);
        }
    }


    @Override
    public Set<EventMetadata> fetchAvailableEvents() {
        try {
            Set<EventMetadata> myEventMetadataSet = new HashSet<>();

            final String myEndPoint = theOddsApiEndpointProvider.sportsEndpoint();
            TheOddsApiSport[] myOddsApiSports = executeGet(myEndPoint, TheOddsApiSport[].class);

            for (TheOddsApiSport myOddsApiSport : myOddsApiSports) {
                League myLeague = TheOddsSchemaConverter.mapTitleToLeague(myOddsApiSport.getTheTitle());
                myEventMetadataSet.addAll(fetchAvailableEvents(myLeague));
            }

            return myEventMetadataSet;
        } catch (Exception myException) {
            throw new RuntimeException("Failed to fetch available events", myException);
        }
    }

    @Override
    public Set<EventMetadata> fetchAvailableEvents(League aLeague) {
        try {
            Set<EventMetadata> myEventMetadataSet = new HashSet<>();
            String myLeagueKey = getLeagueKeyForLeague(aLeague);
            String myEndpoint = theOddsApiEndpointProvider.eventsEndpoint(myLeagueKey);

            TheOddsApiEvent[] myApiEvents = executeGet(myEndpoint, TheOddsApiEvent[].class);

            for (TheOddsApiEvent myEvent : myApiEvents) {
                myEventMetadataSet.add(TheOddsSchemaConverter.mapEventToEventMetadata(myEvent, aLeague));
            }

            return myEventMetadataSet;
        } catch (Exception myException) {
            throw new RuntimeException(
                    "Failed to fetch available events for league: " + aLeague, myException);
        }
    }

    @Override
    public Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport) {
        Map<League, Map<EventId, MarketToBookmakers>> myLeagueMarketToBookMakers = new HashMap<>();
        Set<League> myLeagues = fetchAvailableLeagues(aSport);

        for (League myLeague : myLeagues) {
            myLeagueMarketToBookMakers.put(myLeague, fetchMarketToBookmakers(myLeague));
        }

        return myLeagueMarketToBookMakers;
    }


    @Override
    public Map<League, Map<EventId, MarketToBookmakers>> fetchMarketToBookmakers(Sport aSport, Region aRegion) {
        Set<League> myLeagues = fetchAvailableLeagues(aSport);
        Map<League, Map<EventId, MarketToBookmakers>> myLeagueMarketToBookMakers = new HashMap<>();

        for (League myLeague : myLeagues) {
            myLeagueMarketToBookMakers.put(myLeague, fetchMarketToBookmakers(myLeague, aRegion));
        }

        return myLeagueMarketToBookMakers;
    }

    @Override
    public Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague) {
        Map<EventId, MarketToBookmakers> myMarketToBookmakers = new HashMap<>();

        for (Region myRegion : Region.values()) {
            Map<EventId, MarketToBookmakers> myRegionalMarketToBookmakers = fetchMarketToBookmakers(aLeague, myRegion);

            myRegionalMarketToBookmakers.forEach((anEventId, aRegionalMarketToBookmakers) -> {
                myMarketToBookmakers.computeIfAbsent(anEventId, eventId -> new MarketToBookmakers(new HashMap<>()));

                Map<MarketType, Set<Bookmaker>> combinedMarkets = myMarketToBookmakers.get(anEventId).theMarketToBookmakers();
                Map<MarketType, Set<Bookmaker>> regionalMarkets = aRegionalMarketToBookmakers.theMarketToBookmakers();

                regionalMarkets.forEach((aMarketType, aRegionalBookmakers) ->
                        combinedMarkets.computeIfAbsent(aMarketType, marketType -> new HashSet<>()).addAll(aRegionalBookmakers)
                );
            });
        }

        return myMarketToBookmakers;
    }

    @Override
    public Map<EventId, MarketToBookmakers> fetchMarketToBookmakers(League aLeague, Region aRegion) {
        String myLeagueKey = getLeagueKeyForLeague(aLeague);
        String myEndpoint = theOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegion);

        Map<EventId, MarketToBookmakers> myMarketToBookmakers = new HashMap<>();

        try {
            TheOddsApiOdds[] myApiOdds = executeGet(myEndpoint, TheOddsApiOdds[].class);

            for (TheOddsApiOdds myApiOdd : myApiOdds) {
                String myEventId = myApiOdd.getTheId();
                Map<MarketType, Set<Bookmaker>> myMarketToBookmaker = new HashMap<>();

                // List of all the bookmakers
                for (TheOddsApiBookmaker myApiBookmaker : myApiOdd.getTheBookmakers()) {
                    Bookmaker myBookmaker = Bookmaker.fromBookmakerKey(myApiBookmaker.getTheKey());

                    // List of all the markets
                    for (TheOddsApiMarket myApiMarket : myApiBookmaker.getTheMarkets()) {
                        MarketType myMarketType = TheOddsSchemaConverter.mapMarketKeyToMarketType(myApiMarket.getTheKey());

                        if (!myMarketToBookmaker.containsKey(myMarketType)) {
                            myMarketToBookmaker.put(myMarketType, new HashSet<>());
                        }
                        myMarketToBookmaker.get(myMarketType).add(myBookmaker);
                    }
                }

                // Found all the markets and bookmakers in those markets for a single event
                myMarketToBookmakers.put(new EventId(myEventId), new MarketToBookmakers(myMarketToBookmaker));
            }
            return myMarketToBookmakers;
        } catch (Exception e) {
            // Log and rethrow exception
            theLogger.error("Error fetching odds data from endpoint: {} for league: {} and region: {}. Exception: {}",
                    myEndpoint, aLeague, aRegion, e.getMessage());
            throw new RuntimeException("Failed to fetch market-to-bookmakers mapping", e);
        }
    }


    @Override
    public Set<MarketType> fetchMarketTypes(String anEventId) {
        Set<MarketType> myMarketTypes = new HashSet<>();

        for (Region myRegion : Region.values()) {
            myMarketTypes.addAll(fetchMarketTypes(anEventId, myRegion));
        }

        return myMarketTypes;
    }


    @Override
    public Set<MarketType> fetchMarketTypes(String anEventId, Region aRegion) {
        try {
            // Retrieve the leagueKey using the cache
            String myLeagueKey = getLeagueKeyForEvent(anEventId);

            // Build the endpoint URL with region
            String myEndpoint = theOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegion,
                    Map.of("eventIds", anEventId));

            // Fetch the odds data as an array
            TheOddsApiOdds[] myOddsArray = executeGet(myEndpoint, TheOddsApiOdds[].class);

            // Extract unique market types into a HashSet
            return Arrays.stream(myOddsArray) // Process the array
                    .flatMap(myOdds -> myOdds.getTheBookmakers().stream()) // Flatten bookmakers across all odds
                    .flatMap(bookmaker -> bookmaker.getTheMarkets().stream()) // Flatten markets across all bookmakers
                    .map(TheOddsApiMarket::getTheKey) // Extract market keys
                    .map(aMarketKey -> {
                        MarketType marketType = TheOddsSchemaConverter.mapMarketKeyToMarketType(aMarketKey);
                        if (marketType == null) {
                            theLogger.warn("Unknown market key '{}' for event '{}' in region '{}'", aMarketKey, anEventId, aRegion);
                        }
                        return marketType;
                    })
                    .filter(Objects::nonNull) // Filter out null values
                    .collect(Collectors.toCollection(HashSet::new)); // Collect explicitly into a HashSet
        } catch (Exception e) {
            theLogger.error("Failed to fetch market types for event '{}' and region '{}'", anEventId, aRegion, e);
            return Set.of();
        }
    }

    @Override
    public Set<Bookmaker> fetchBookmakers(String anEventId, MarketType aMarketType) {
        try {
            // Loop through all available regions and aggregate results
            return Arrays.stream(Region.values()) // Iterate over all regions
                    .map(region -> fetchBookmakers(anEventId, aMarketType, region)) // Use the overloaded function
                    .flatMap(Set::stream) // Flatten all results into a single stream
                    .collect(Collectors.toSet()); // Collect unique bookmakers
        } catch (Exception e) {
            theLogger.error("Failed to fetch bookmakers for event '{}' and market type '{}'", anEventId, aMarketType, e);
            return Set.of();
        }
    }

    @Override
    public Set<Bookmaker> fetchBookmakers(String anEventId, MarketType aMarketType, Region aRegion) {
        try {
            // Retrieve the leagueKey using the cache
            String myLeagueKey = getLeagueKeyForEvent(anEventId);

            // Build the endpoint URL with region
            String myEndpoint = theOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegion, Map.of("eventIds", anEventId));

            // Fetch the odds data as an array
            TheOddsApiOdds[] myOddsArray = executeGet(myEndpoint, TheOddsApiOdds[].class);

            // Extract unique bookmakers that offer the specified MarketType
            return Arrays.stream(myOddsArray) // Process the odds array
                    .flatMap(myOdds -> myOdds.getTheBookmakers().stream()) // Flatten bookmakers
                    .filter(bookmaker -> bookmaker.getTheMarkets()
                            .stream()
                            .anyMatch(market -> market.getTheKey().equalsIgnoreCase(aMarketType.name()))) // Filter by MarketType
                    .map(bookmaker -> Bookmaker.fromBookmakerKey(bookmaker.getTheKey())) // Map to Bookmaker enum
                    .filter(Objects::nonNull) // Filter out null values
                    .collect(Collectors.toSet()); // Collect unique bookmakers
        } catch (Exception e) {
            theLogger.error("Failed to fetch bookmakers for event '{}', market type '{}', and region '{}'",
                    anEventId, aMarketType, aRegion, e);
            return Set.of();
        }
    }

    @Override
    public List<Odds> fetchOdds(String anEventId, MarketType aMarketType) {
        try {
            // Loop through all available regions and aggregate odds
            return Arrays.stream(Region.values()) // Iterate over all regions
                    .flatMap(region -> fetchOdds(anEventId, aMarketType, region).stream()) // Use the region-specific method
                    .toList(); // Collect results into a List
        } catch (Exception e) {
            theLogger.error("Failed to fetch odds for event '{}' and market type '{}'", anEventId, aMarketType, e);
            return List.of();
        }
    }


    @Override
    public List<Odds> fetchOdds(String anEventId, MarketType aMarketType, Region aRegion) {
        try {
            String myLeagueKey = getLeagueKeyForEvent(anEventId);
            Map<String, String> myOptionalParams = new HashMap<>();
            myOptionalParams.put("eventIds", anEventId);
            myOptionalParams.put("markets", aMarketType.name().toLowerCase());

            String myEndpoint = theOddsApiEndpointProvider.oddsEndpoint(myLeagueKey, aRegion, myOptionalParams);

            TheOddsApiOdds[] myOddsArray = executeGet(myEndpoint, TheOddsApiOdds[].class);

            // Extract and map odds
            return Arrays.stream(myOddsArray) // Process the odds array
                    .flatMap(myOdds -> myOdds.getTheBookmakers().stream() // Flatten bookmakers
                            .map(bookmaker -> TheOddsSchemaConverter.mapToOdds(
                                    anEventId,
                                    myLeagueKey,
                                    aMarketType,
                                    myOdds.getTheHomeTeam(), // Get home team name
                                    myOdds.getTheAwayTeam(), // Get away team name
                                    bookmaker))) // Map to Odds
                    .filter(Objects::nonNull) // Remove any null mappings
                    .toList(); // Collect results into a List
        } catch (Exception e) {
            theLogger.error("Failed to fetch odds for event '{}', market type '{}', and region '{}'", anEventId, aMarketType, aRegion, e);
            return List.of();
        }
    }

    @Override
    public boolean isApiAvailable() {
        // Could do a simple health check by calling /v4/sports?apiKey and see if it returns 200
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private <T> T executeGet(String anEndpoint, Class<T> aResponseType) throws Exception {
        // TODO: Remove this
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

    private String getLeagueKeyForEvent(String anEventId) {
        // Check if the leagueKey is already cached
        if (theEventIdToLeagueKeyCache.containsKey(anEventId)) {
            return theEventIdToLeagueKeyCache.get(anEventId);
        }

        // Load data into the cache if not present
        synchronized (theEventIdToLeagueKeyCache) { // Ensure thread safety during cache population
            if (!theEventIdToLeagueKeyCache.containsKey(anEventId)) {
                populateEventToLeagueKeyCache();
            }
        }

        // Return the leagueKey from the cache
        return theEventIdToLeagueKeyCache.get(anEventId);
    }

    private void populateEventToLeagueKeyCache() {
        try {
            // Step 1: Fetch all sports
            String mySportsEndpoint = theOddsApiEndpointProvider.sportsEndpoint();
            TheOddsApiSport[] mySports = executeGet(mySportsEndpoint, TheOddsApiSport[].class);

            // Step 2: For each sport, fetch events and populate the cache
            for (TheOddsApiSport mySport : mySports) {
                String myLeagueKey = mySport.getTheKey(); // Extract the leagueKey (sportKey)

                // Call the events endpoint for each sport
                String myEventsEndpoint = theOddsApiEndpointProvider.eventsEndpoint(myLeagueKey);
                TheOddsApiEvent[] myEvents = executeGet(myEventsEndpoint, TheOddsApiEvent[].class);

                // Map each eventId to its leagueKey
                for (TheOddsApiEvent myEvent : myEvents) {
                    theEventIdToLeagueKeyCache.put(myEvent.getTheId(), myLeagueKey);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate the EventId-to-LeagueKey cache", e);
        }
    }

    private String getLeagueKeyForLeague(League aLeague) {
        // Check if the league key is already cached
        if (theLeagueToKeyCache.containsKey(aLeague)) {
            return theLeagueToKeyCache.get(aLeague);
        }

        // Load data into the cache if not present
        synchronized (theLeagueToKeyCache) { // Ensure thread safety during cache population
            if (!theLeagueToKeyCache.containsKey(aLeague)) {
                populateLeagueToKeyCache();
            }
        }

        // Return the league key from the cache
        return theLeagueToKeyCache.get(aLeague);
    }

    private void populateLeagueToKeyCache() {
        try {
            String mySportsEndpoint = theOddsApiEndpointProvider.sportsEndpoint();
            TheOddsApiSport[] mySports = executeGet(mySportsEndpoint, TheOddsApiSport[].class);

            for (TheOddsApiSport mySport : mySports) {
                League myLeague = TheOddsSchemaConverter.mapTitleToLeague(mySport.getTheTitle());

                if (myLeague != null) {
                    theLeagueToKeyCache.put(myLeague, mySport.getTheKey());
                } else {
                    theLogger.error("No matching league representation for title '{}'", mySport.getTheTitle());
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate the League-to-Key cache", e);
        }
    }

}
