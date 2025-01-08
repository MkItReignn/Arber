package com.arber.api.datamodel;

public enum OperationContext {
    FETCH_SPORTS("Fetch Sports"),
    FETCH_LEAGUES("Fetch Leagues"),
    FETCH_LEAGUE_METADATA("Fetch League Metadata"),
    FETCH_EVENT_METADATA("Fetch Event Metadata"),
    FETCH_MARKET_TO_BOOKMAKER("Fetch Market To BookMaker"),
    FETCH_MARKET_TYPES("Fetch Market Types"),
    FETCH_ODDS_METADATA("Fetch Odds Metadata"),;

    private final String theDescription;

    OperationContext(String aDescription) {
        theDescription = aDescription;
    }

    @Override
    public String toString() {
        return theDescription;
    }
}
