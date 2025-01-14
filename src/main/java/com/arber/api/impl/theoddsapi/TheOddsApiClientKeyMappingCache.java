package com.arber.api.impl.theoddsapi;

import com.arber.datamodel.League;
import com.arber.datamodel.EventId;
import com.arber.datamodel.LeagueKey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class TheOddsApiClientKeyMappingCache {
    private final ConcurrentMap<EventId, LeagueKey> theEventIdToLeagueKeyCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<League, LeagueKey> theLeagueToKeyCache = new ConcurrentHashMap<>();

    public void addEventToLeagueMapping(EventId anEventId, LeagueKey aLeagueKey) {
        theEventIdToLeagueKeyCache.put(anEventId, aLeagueKey);
    }

    public void addLeagueToKeyMapping(League aLeague, LeagueKey aLeagueKey) {
        theLeagueToKeyCache.put(aLeague, aLeagueKey);
    }

    public LeagueKey getLeagueKeyForEvent(EventId anEventId) {
        return theEventIdToLeagueKeyCache.get(anEventId);
    }

    public LeagueKey getLeagueKeyForLeague(League aLeague) {
        return theLeagueToKeyCache.get(aLeague);
    }
}
