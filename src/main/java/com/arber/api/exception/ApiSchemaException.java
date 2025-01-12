package com.arber.api.exception;

public class ApiSchemaException extends RuntimeException {
    public ApiSchemaException(String aMessage) {
        super(aMessage);
    }
    public ApiSchemaException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
