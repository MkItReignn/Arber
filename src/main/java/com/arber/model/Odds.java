package com.arber.model;

import java.time.ZonedDateTime;
import com.arber.enums.BookmakerType;
import com.arber.enums.Region;

public class Odds {
    private final BookmakerType theBookmakerType;
    private final double theHomeOdds;
    private final double theAwayOdds;
    private final ZonedDateTime theLastUpdate;

    public Odds(BookmakerType aBookmakerType, double aHomeOdds, double aAwayOdds, ZonedDateTime aLastUpdate) {
        this.theBookmakerType = aBookmakerType;
        this.theHomeOdds = aHomeOdds;
        this.theAwayOdds = aAwayOdds;
        this.theLastUpdate = aLastUpdate;
    }

    public final BookmakerType getBookmakerType() {
        return theBookmakerType;
    }

    public final double getHomeOdds() {
        return theHomeOdds;
    }

    public final double getAwayOdds() {
        return theAwayOdds;
    }

    public final ZonedDateTime getLastUpdate() {
        return theLastUpdate;
    }

    public final Region getRegion() {
        return theBookmakerType.getRegion();
    }

    @Override
    public String toString() {
        return String.format(
                "Odds{bookmaker='%s', homeOdds=%.2f, awayOdds=%.2f, lastUpdate=%s, region='%s'}",
                theBookmakerType, theHomeOdds, theAwayOdds, theLastUpdate, theBookmakerType.getRegion()
        );
    }
}
