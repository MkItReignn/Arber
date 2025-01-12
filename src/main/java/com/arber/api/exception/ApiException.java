package com.arber.api.exception;

public class ApiException extends Exception {
    public ApiException(String aMessage) {
        super(aMessage);
    }
    public ApiException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
