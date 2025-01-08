package com.arber.api.impl.theoddsapi;

import com.arber.api.exception.EndpointParameterValidationException;
import com.arber.datamodel.MarketType;
import com.arber.datamodel.Region;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TheOddsApiEndpointParameterValidator {
    private static final Set<String> VALID_MARKETS = Arrays.stream(MarketType.values())
            .map(enumValue -> enumValue.name().toLowerCase())
            .collect(Collectors.toSet());
    private static final Set<String> VALID_REGIONS = Arrays.stream(Region.values())
            .map(regionValue -> regionValue.name().toLowerCase())
            .collect(Collectors.toSet());
    private static final Predicate<String> IS_BOOLEAN = value -> "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    private static final Predicate<String> IS_REGION = value -> value != null && Arrays.stream(value.split(",")).allMatch(VALID_REGIONS::contains);
    private static final Predicate<String> IS_MARKETS = value -> value != null && VALID_MARKETS.containsAll(Set.of(value.split(",")));
    private static final Predicate<String> IS_ODDS_FORMAT = value -> "decimal".equalsIgnoreCase(value) || "american".equalsIgnoreCase(value);
    private static final Predicate<String> IS_DATE_FORMAT = value -> "unix".equalsIgnoreCase(value) || "iso".equalsIgnoreCase(value);
    private static final Predicate<String> IS_ISO_DATE = value -> value != null && value.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$");
    private static final Predicate<String> IS_EVENT_IDS = value -> value != null && value.matches("^[a-fA-F0-9]{32}(,[a-fA-F0-9]{32})*$");
    private static final Predicate<String> IS_DAYS_FROM = value -> value != null && value.matches("^[1-3]$");

    private TheOddsApiEndpointParameterValidator() {}

    private static final Map<String, Predicate<String>> PARAMETER_VALIDATORS = Map.ofEntries(
            Map.entry("all", IS_BOOLEAN),
            Map.entry("regions", IS_REGION),
            Map.entry("markets", IS_MARKETS),
            Map.entry("oddsFormat", IS_ODDS_FORMAT),
            Map.entry("dateFormat", IS_DATE_FORMAT),
            Map.entry("date", IS_ISO_DATE),
            Map.entry("eventIds", IS_EVENT_IDS),
            Map.entry("commenceTimeFrom", IS_ISO_DATE),
            Map.entry("commenceTimeTo", IS_ISO_DATE),
            Map.entry("includeLinks", IS_BOOLEAN),
            Map.entry("includeSids", IS_BOOLEAN),
            Map.entry("includeBetLimits", IS_BOOLEAN),
            Map.entry("daysFrom", IS_DAYS_FROM)
    );

    public static void validateParameters(Map<String, String> aParameters) {
        for (Map.Entry<String, String> myEntry : aParameters.entrySet()) {
            String myKey = myEntry.getKey();
            String myValue = myEntry.getValue();

            Predicate<String> myValidator = PARAMETER_VALIDATORS.get(myKey);

            if (myValidator == null) {
                throw new EndpointParameterValidationException("Invalid parameter key: " + myKey);
            }

            if (!myValidator.test(myValue)) {
                throw new EndpointParameterValidationException("Invalid value for parameter '" + myKey + "': " + myValue);
            }
        }
    }
}