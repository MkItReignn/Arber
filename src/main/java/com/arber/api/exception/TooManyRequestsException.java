package com.arber.api.exception;

public class TooManyRequestsException extends ApiException {
    public TooManyRequestsException(String aMessage) {
        super(aMessage);
    }
    public TooManyRequestsException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
