package com.arber.api.exception;

public class InvalidResponseException extends ApiException {
    public InvalidResponseException(String aMessage) {
        super(aMessage);
    }
    public InvalidResponseException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}