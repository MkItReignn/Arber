package com.arber.utils.errors;

@FunctionalInterface
public interface ExceptionHandler <ExceptionType extends Exception> {
    void handle(ExceptionType anException, Object aContext);
}