package com.arber.model.TheOddsApi;

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
        this.theName = aName;
        this.theDescription = aDescription;
        this.thePrice = aPrice;
        this.thePoint = aPoint;
    }

    public String getTheName() {
        return theName;
    }

    public String getTheDescription() {
        return theDescription;
    }

    public double getThePrice() {
        return thePrice;
    }

    public Double getThePoint() {
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
