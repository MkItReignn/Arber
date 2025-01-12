package com.arber.api.exception;

public class SchemaMappingException extends ApiSchemaException {
    public SchemaMappingException(String aMessage) {
        super(aMessage);
    }
    public SchemaMappingException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
