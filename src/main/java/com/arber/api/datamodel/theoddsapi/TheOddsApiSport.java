package com.arber.api.datamodel.theoddsapi;

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
        theKey = aKey;
        theGroup = aGroup;
        theTitle = aTitle;
        theDescription = aDescription;
        theIsActive = aIsActive;
        theHasOutrights = aHasOutrights;
    }

    public String getKey() {
        return theKey;
    }

    public String getGroup() {
        return theGroup;
    }

    public String getTitle() {
        return theTitle;
    }

    public String getDescription() {
        return theDescription;
    }

    public boolean getIsActive() {
        return theIsActive;
    }

    public boolean getHasOutrights() {
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
