package com.arber.model.theoddsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TheOddsApiSport {
    @JsonProperty("key")
    public String theKey;
    @JsonProperty("group")
    public String theGroup;
    @JsonProperty("title")
    public String theTitle;
    @JsonProperty("description")
    public String theDescription;
    @JsonProperty("active")
    public boolean theIsActive;
    @JsonProperty("has_outrights")
    public boolean theHasOutrights;

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
