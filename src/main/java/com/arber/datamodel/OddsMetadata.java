package com.arber.datamodel;

import com.arber.autotrader.datamodel.BookmakerGroupingKey;
import com.arber.autotrader.datamodel.Odds;

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

    public BookmakerGroupingKey getGroupingKey() {
        return new BookmakerGroupingKey(theEventId, theMarketType, theBookmaker.getRegion());
    }

    public Odds getOdds() {
        return new Odds(
                theEventId,
                theHomeTeam,
                theAwayTeam,
                theHomeOdds,
                theAwayOdds,
                theLastUpdateMillis
        );
    }
}