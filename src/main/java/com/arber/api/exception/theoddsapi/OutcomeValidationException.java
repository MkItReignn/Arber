package com.arber.api.exception.theoddsapi;

public class OutcomeValidationException extends RuntimeException {
    public OutcomeValidationException(String aMessage) {
        super(aMessage);
    }
    public OutcomeValidationException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
