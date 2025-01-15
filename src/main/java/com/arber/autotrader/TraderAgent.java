package com.arber.autotrader;

import com.arber.autotrader.config.AgentConfig;
import com.arber.autotrader.datamodel.BookmakerGroupingKey;
import com.arber.autotrader.datamodel.Odds;
import com.arber.autotrader.strategy.TradingStrategy;
import com.arber.autotrader.tradeaction.TradeActionHandler;
import com.arber.cache.MetadataCache;
import com.arber.datamodel.*;
import com.arber.events.datamodel.EventMetadataUpdateEvent;
import com.arber.events.datamodel.OddsMetadataUpdateEvent;
import com.arber.events.datamodel.SportsEvent;
import com.arber.events.eventbus.EventBus;

import java.util.*;
import java.util.function.Consumer;

public class TraderAgent implements Consumer<SportsEvent> {
    private final AgentConfig theConfig;
    private final List<TradingStrategy> theStrategies;
    private final TradeActionHandler theTradeActionHandler;
    private final Map<BookmakerGroupingKey, Map<Bookmaker, Odds>> theOddsMetadataRegistry;

    private TraderAgent(AgentConfig aConfig,
                        List<TradingStrategy> aStrategies,
                        TradeActionHandler aTradeActionHandler,
                        MetadataCache aMetadataCache) {
        theConfig = aConfig;
        theStrategies = aStrategies;
        theTradeActionHandler = aTradeActionHandler;
        theOddsMetadataRegistry = new HashMap<>();
        initializeOddsMetadataRegistry(aMetadataCache);
    }

    public void subscribeTo(EventBus eventBus) {
        eventBus.subscribe(this);
    }

    @Override
    public void accept(SportsEvent anEvent) {
        if (anEvent instanceof EventMetadataUpdateEvent) {
            handleEventMetadataUpdate((EventMetadataUpdateEvent) anEvent);
        } else if (anEvent instanceof OddsMetadataUpdateEvent) {
            handleOddsMetadataUpdate((OddsMetadataUpdateEvent) anEvent);
        }
    }

    private void handleEventMetadataUpdate(EventMetadataUpdateEvent anEvent) {
        Set<EventMetadata> myEventMetadataSet = anEvent.theEventMetadata();
        for (EventMetadata myEventMetadata : myEventMetadataSet) {
            EventId myNewEventId = myEventMetadata.theEventId();
            populateRegistryWithKeys(myNewEventId);
        }
    }

    private void handleOddsMetadataUpdate(OddsMetadataUpdateEvent anEvent) {
        OddsMetadata myOddsMetadata = anEvent.theOdds();
        Bookmaker myBookmaker = myOddsMetadata.theBookmaker();
        BookmakerGroupingKey myKey = myOddsMetadata.getGroupingKey();

        if (!theOddsMetadataRegistry.containsKey(myKey)) {
            return;
        }

        Odds myOdds = myOddsMetadata.getOdds();
        updateOddsRegistry(myKey, myBookmaker, myOdds);
        evaluateTradingStrategies(anEvent);
    }

    private void updateOddsRegistry(BookmakerGroupingKey aKey, Bookmaker aBookmaker, Odds anOdd) {
        theOddsMetadataRegistry.get(aKey).put(aBookmaker, anOdd);
    }

    private void evaluateTradingStrategies(SportsEvent anEvent) {
        for (TradingStrategy myStrategy : theStrategies) {
            myStrategy.evaluate(anEvent, theOddsMetadataRegistry)
                    .ifPresent(theTradeActionHandler::handle);
        }
    }

    private void initializeOddsMetadataRegistry(MetadataCache aMetadataCache) {
        for (League myLeague : theConfig.getTheLeagues()) {
            Set<EventMetadata> myRelevantEvents = aMetadataCache.getEventsMetadata().get(myLeague);
            for (EventMetadata myEvent : myRelevantEvents) {
                EventId myRelevantEventId = myEvent.theEventId();
                populateRegistryWithKeys(myRelevantEventId);
            }
        }
    }

    private void populateRegistryWithKeys(EventId aNewEventId) {
        theConfig.getTheMarkets().forEach(myRelevantMarket ->
                theConfig.getTheRegions().forEach(myRelevantRegion -> {
                    BookmakerGroupingKey myGroupingKey = new BookmakerGroupingKey(aNewEventId, myRelevantMarket, myRelevantRegion);
                    theOddsMetadataRegistry.putIfAbsent(myGroupingKey, new EnumMap<>(Bookmaker.class));
                })
        );
    }
}