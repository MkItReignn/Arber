package com.arber.api.impl.theoddsapi;

public class TheOddsApiMetadataFactory {
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_VERSION = System.getenv("API_VERSION");
    private static final String BASE_URL = System.getenv("BASE_URL");
    private static final String API_NAME = System.getenv("API_NAME");

    static {
        java.util.function.Predicate<String> isNullOrEmpty = myVariable -> myVariable == null ||
                myVariable.trim().isEmpty();

        if (isNullOrEmpty.test(API_KEY)) {
            throw new IllegalStateException("Environment variable 'API_KEY' is not set or is empty.");
        }
        if (isNullOrEmpty.test(API_VERSION)) {
            throw new IllegalStateException("Environment variable 'API_VERSION' is not set or is empty.");
        }
        if (isNullOrEmpty.test(BASE_URL)) {
            throw new IllegalStateException("Environment variable 'BASE_URL' is not set or is empty.");
        }
        if (isNullOrEmpty.test(API_NAME)) {
            throw new IllegalStateException("Environment variable 'API_NAME' is not set or is empty.");
        }
    }

    public static TheOddsApiMetadata provideTheOddsApiVersionMetadata() {
        return new TheOddsApiMetadata(API_KEY, API_VERSION, BASE_URL, API_NAME);
    }
}
