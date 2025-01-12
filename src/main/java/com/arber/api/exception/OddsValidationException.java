package com.arber.api.exception;

public class OddsValidationException extends RuntimeException {
    public OddsValidationException(String aMessage) {
        super(aMessage);
    }
    public OddsValidationException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
