package com.arber.model.TheOddsApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheOddsApiSport {

    private final String theKey;
    private final String theGroup;
    private final String theTitle;
    private final String theDescription;
    private final boolean theIsActive;
    private final boolean theHasOutrights;

    @JsonCreator
    public TheOddsApiSport(@JsonProperty("key") String aKey,
                           @JsonProperty("group") String aGroup,
                           @JsonProperty("title") String aTitle,
                           @JsonProperty("description") String aDescription,
                           @JsonProperty("active") boolean aIsActive,
                           @JsonProperty("has_outrights") boolean aHasOutrights) {
        this.theKey = aKey;
        this.theGroup = aGroup;
        this.theTitle = aTitle;
        this.theDescription = aDescription;
        this.theIsActive = aIsActive;
        this.theHasOutrights = aHasOutrights;
    }

    public String getTheKey() {
        return theKey;
    }

    public String getTheGroup() {
        return theGroup;
    }

    public String getTheTitle() {
        return theTitle;
    }

    public String getTheDescription() {
        return theDescription;
    }

    public boolean getTheIsActive() {
        return theIsActive;
    }

    public boolean getTheHasOutrights() {
        return theHasOutrights;
    }

    @Override
    public String toString() {
        return "TheOddsApiSport {" +
                "theKey='" + theKey + '\'' +
                ", theGroup='" + theGroup + '\'' +
                ", theTitle='" + theTitle + '\'' +
                ", theDescription='" + theDescription + '\'' +
                ", theIsActive=" + theIsActive +
                ", theHasOutrights=" + theHasOutrights +
                '}';
    }
}
