package com.arber.api.exception;

public class SchemaValidationException extends ApiSchemaException {
    public SchemaValidationException(String aMessage) {
        super(aMessage);
    }

    public SchemaValidationException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
