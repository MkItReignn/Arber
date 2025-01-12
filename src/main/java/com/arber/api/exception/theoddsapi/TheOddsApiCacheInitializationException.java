package com.arber.api.exception.theoddsapi;

public class TheOddsApiCacheInitializationException extends RuntimeException {
    public TheOddsApiCacheInitializationException(String aMessage) {
        super(aMessage);
    }
    public TheOddsApiCacheInitializationException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
