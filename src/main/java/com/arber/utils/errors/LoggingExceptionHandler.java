package com.arber.utils.errors;

import org.slf4j.Logger;

public class LoggingExceptionHandler<ExceptionType extends Exception> implements ExceptionHandler<ExceptionType> {
    private final Logger theLogger;

    public LoggingExceptionHandler(Logger aLogger) {
        theLogger = aLogger;
    }

    @Override
    public void handle(ExceptionType anException, Object aContext) {
        theLogger.error(anException.getMessage(), aContext);
    }
}
