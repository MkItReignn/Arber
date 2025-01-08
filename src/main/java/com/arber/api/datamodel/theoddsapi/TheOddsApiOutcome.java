package com.arber.api.datamodel.theoddsapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheOddsApiOutcome {

    private final String theName;
    private final String theDescription; // Optional field
    private final double thePrice;
    private final Double thePoint; // Optional field

    @JsonCreator
    public TheOddsApiOutcome(@JsonProperty("name") String aName,
                             @JsonProperty("description") String aDescription,
                             @JsonProperty("price") double aPrice,
                             @JsonProperty("point") Double aPoint) {
        theName = aName;
        theDescription = aDescription;
        thePrice = aPrice;
        thePoint = aPoint;
    }

    public String getName() {
        return theName;
    }

    public String getDescription() {
        return theDescription;
    }

    public double getPrice() {
        return thePrice;
    }

    public Double getPoint() {
        return thePoint;
    }

    @Override
    public String toString() {
        return "TheOddsApiOutcome {" +
                "theName='" + theName + '\'' +
                ", theDescription='" + theDescription + '\'' +
                ", thePrice=" + thePrice +
                ", thePoint=" + thePoint +
                '}';
    }
}
