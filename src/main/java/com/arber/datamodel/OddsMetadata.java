package com.arber.datamodel;

public record OddsMetadata(
        EventId theEventId,
        League theLeague,
        MarketType theMarketType,
        Bookmaker theBookmaker,
        Participant theHomeTeam,
        Participant theAwayTeam,
        double theHomeOdds,
        double theAwayOdds,
        long theLastUpdateMillis
) {
    public OddsMetadataId getId() {
        return new OddsMetadataId(theEventId, theMarketType, theBookmaker);
    }
}