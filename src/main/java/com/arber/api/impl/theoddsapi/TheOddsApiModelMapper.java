package com.arber.api.impl.theoddsapi;

import com.arber.api.exception.*;
import com.arber.api.exception.OddsValidationException;
import com.arber.api.exception.theoddsapi.OutcomeValidationException;
import com.arber.datamodel.*;
import com.arber.api.datamodel.theoddsapi.*;
import com.arber.utils.EpochConverter;
import com.arber.utils.errors.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

final class TheOddsApiModelMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TheOddsApiModelMapper.class);

    private TheOddsApiModelMapper() {}
    public static Set<Sport> mapSports(TheOddsApiSport[] anApiSports, ErrorHandler anErrorHandler) {
        return Arrays.stream(anApiSports)
                .map(anErrorHandler.wrapFunctionWithErrorHandling(
                        anApiSport -> TheOddsApiSchemaConverter.mapGroupToSport(anApiSport.getGroup())))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static Set<League> mapLeagues(TheOddsApiSport[] anApiSports, Sport aTargetSport, ErrorHandler anErrorHandler) {
        return Arrays.stream(anApiSports)
                .filter(anErrorHandler.wrapPredicateWithErrorHandling(
                        apiSport -> matchesTargetSport(apiSport, aTargetSport)))
                .map(anErrorHandler.wrapFunctionWithErrorHandling(
                        apiSport -> TheOddsApiSchemaConverter.mapTitleToLeague(apiSport.getTitle())))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static Set<LeagueMetadata> mapLeagueMetadata(TheOddsApiSport[] anApiSports, Sport aTargetSport, ErrorHandler anErrorHandler) {
        return Arrays.stream(anApiSports)
                .filter(anErrorHandler.wrapPredicateWithErrorHandling(
                        apiSport -> matchesTargetSport(apiSport, aTargetSport)))
                .map(anErrorHandler.wrapFunctionWithErrorHandling(
                        apiSport -> createLeagueMetadata(apiSport, aTargetSport)))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static Set<EventMetadata> mapEventMetadata(TheOddsApiEvent[] anApiEvents, League aLeague) {
        return Arrays.stream(anApiEvents)
                .map(event -> mapEventToEventMetadata(event, aLeague))
                .collect(Collectors.toSet());
    }

    public static Map<EventId, MarketToBookmakers> mapMarketToBookmakers(TheOddsApiOdds[] anApiOdds) {
        Map<EventId, MarketToBookmakers> myMarketToBookmakers = new HashMap<>();
        for (TheOddsApiOdds anApiOdd : anApiOdds) {
            Map<MarketType, Set<Bookmaker>> marketBookmakers = new HashMap<>();

            for (TheOddsApiBookmaker anApiBookmaker : anApiOdd.getBookmakers()) {
                Bookmaker aBookmaker = Bookmaker.fromBookmakerKey(anApiBookmaker.getBookmakerKey());

                for (TheOddsApiMarket anApiMarket : anApiBookmaker.getMarkets()) {
                    try {
                        MarketType myMarketType = TheOddsApiSchemaConverter.mapMarketKeyToMarketType(anApiMarket.getMarketKey());
                        marketBookmakers.computeIfAbsent(myMarketType, k -> new HashSet<>()).add(aBookmaker);
                    } catch (SchemaMappingException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }

            myMarketToBookmakers.put(new EventId(anApiOdd.getId()), new MarketToBookmakers(marketBookmakers));
        }
        return myMarketToBookmakers;
    }

    public static EventMetadata mapEventToEventMetadata(TheOddsApiEvent anEvent, League aLeague) {
        return new EventMetadata(
                anEvent.getId(),
                aLeague.getSport(),
                aLeague,
                anEvent.getSportKey(),
                anEvent.getHomeTeam(),
                anEvent.getAwayTeam(),
                EpochConverter.toEpochMilliseconds(anEvent.getCommenceTime())
        );
    }

    public static List<OddsMetadata> mapToOddsMetadataList(
            TheOddsApiOdds[] anApiOdds,
            LeagueKey aLeagueKey,
            MarketType aMarketType,
            ErrorHandler anErrorHandler) {
        return Arrays.stream(anApiOdds)
                .flatMap(apiOdds -> apiOdds.getBookmakers().stream()
                        .map(anErrorHandler.wrapFunctionWithErrorHandling(bookmaker ->
                                createOddsMetadata(bookmaker, apiOdds, new EventId(apiOdds.getId()), aLeagueKey, aMarketType))))
                .filter(Objects::nonNull)
                .toList();
    }

    private static OddsMetadata createOddsMetadata(TheOddsApiBookmaker aBookmaker, TheOddsApiOdds anApiOdds,
                                                   EventId anEventId, LeagueKey aLeagueKey, MarketType aMarketType)
            throws SchemaValidationException {
        TheOddsApiMarket market = findApiMarketByMarketType(aBookmaker.getMarkets(), aMarketType);
        List<TheOddsApiOutcome> outcomes = market.getOutcomes();
        Bookmaker mappedBookmaker = Bookmaker.fromBookmakerKey(aBookmaker.getBookmakerKey());
        long myLastUpdate = EpochConverter.toEpochMilliseconds(aBookmaker.getLastUpdate());

        return mapToOddsMetadata(
                outcomes,
                mappedBookmaker,
                myLastUpdate,
                anEventId,
                aLeagueKey,
                aMarketType,
                anApiOdds.getHomeTeam(),
                anApiOdds.getAwayTeam()
        );
    }

    public static OddsMetadata mapToOddsMetadata(List<TheOddsApiOutcome> anApiOutcomes, Bookmaker aBookmaker,
                                                 long aLastUpdate, EventId anEventId, LeagueKey aLeagueKey,
                                                 MarketType aMarketType, String aHomeTeam, String anAwayTeam) {
        double myHomeOdds = 0.0;
        double myAwayOdds = 0.0;

        for (TheOddsApiOutcome outcome : anApiOutcomes) {
            if (outcome.getName().equalsIgnoreCase(aHomeTeam)) {
                myHomeOdds = outcome.getPrice();
            } else if (outcome.getName().equalsIgnoreCase(anAwayTeam)) {
                myAwayOdds = outcome.getPrice();
            } else {
                throw new OutcomeValidationException(String.format("Outcome name '%s' does not match home or away team for event '%s'",
                        outcome.getName(), anEventId));
            }
        }

        if (myHomeOdds == 0.0 || myAwayOdds == 0.0) {
            throw new OddsValidationException(String.format("Incomplete odds for event '%s', market type '%s', bookmaker '%s'",
                    anEventId, aMarketType, aBookmaker.getBookmakerKey()));
        }

        return new OddsMetadata(
                anEventId,
                aLeagueKey,
                aMarketType,
                aBookmaker,
                new Participant(aHomeTeam),
                new Participant(anAwayTeam),
                myHomeOdds,
                myAwayOdds,
                aLastUpdate
        );
    }

    public static TheOddsApiMarket findApiMarketByMarketType(List<TheOddsApiMarket> anApiMarkets,
                                                             MarketType aMarketType) throws SchemaValidationException {
        return anApiMarkets.stream()
                .filter(market -> market.getMarketKey().equalsIgnoreCase(aMarketType.name()))
                .findFirst()
                .orElseThrow(() -> new SchemaValidationException(String.format("MarketType '%s' could not be found in" +
                        "List<TheOddsApiMarket> '%s'", aMarketType, anApiMarkets)));
    }

    private static boolean matchesTargetSport(TheOddsApiSport apiSport, Sport aTargetSport) throws SchemaMappingException {
        return TheOddsApiSchemaConverter.mapGroupToSport(apiSport.getGroup()) == aTargetSport;
    }

    private static LeagueMetadata createLeagueMetadata(TheOddsApiSport apiSport, Sport targetSport) throws SchemaMappingException {
        League league = TheOddsApiSchemaConverter.mapTitleToLeague(apiSport.getTitle());
        return new LeagueMetadata(
                targetSport,
                league,
                apiSport.getKey(),
                apiSport.getTitle(),
                Optional.ofNullable(apiSport.getDescription())
        );
    }

}
