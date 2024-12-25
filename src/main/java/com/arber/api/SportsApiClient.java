package com.arber.api;

import com.arber.enums.BookmakerType;
import com.arber.enums.MarketType;
import com.arber.enums.Sport;
import com.arber.enums.League;
import com.arber.model.Event;
import com.arber.model.Odds;
import java.util.List;
import java.util.Set;

public interface SportsApiClient {

    Set<Sport> fetchAvailableSports();

    Set<League> fetchAvailableLeagues();
    List<League> fetchAvailableLeagues(Sport aSport);

    List<Event> fetchEvents(League aLeague);

    List<MarketType> fetchMarketTypes(String anEventId);

    List<BookmakerType> fetchBookmakerTypes(String anEventId);

    List<Odds> fetchOdds(String anEventId);
    List<Odds> fetchOdds(String anEventId, BookmakerType aBookmakerType);


    boolean isApiAvailable();
    String getApiName();
}
