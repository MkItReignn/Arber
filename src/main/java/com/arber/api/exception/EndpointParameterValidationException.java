package com.arber.api.exception;

public class EndpointParameterValidationException extends RuntimeException {
    public EndpointParameterValidationException(String aMessage) {
        super(aMessage);
    }
    public EndpointParameterValidationException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
