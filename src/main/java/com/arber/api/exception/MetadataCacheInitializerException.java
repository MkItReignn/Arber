package com.arber.api.exception;

public class MetadataCacheInitializerException extends Exception {
    public MetadataCacheInitializerException(String aMessage) {
        super(aMessage);
    }
    public MetadataCacheInitializerException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
