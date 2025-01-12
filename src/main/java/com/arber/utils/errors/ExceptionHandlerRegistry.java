package com.arber.utils.errors;

import java.util.*;

public class ExceptionHandlerRegistry {
    private final ClassValue<List<ExceptionHandler<? extends Exception>>> handlers = new ClassValue<>(){
        @Override
        protected List<ExceptionHandler<? extends Exception>> computeValue(Class<?> type) {
            return Collections.synchronizedList(new ArrayList<>());
        }
    };

    public <ExceptionType extends Exception> void addHandler(
            Class<ExceptionType> exceptionType,
            ExceptionHandler<ExceptionType> handler) {
        handlers.get(exceptionType)
                .add(handler);
    }

    @SuppressWarnings("unchecked")
    public <ExceptionType extends Exception> List<ExceptionHandler<ExceptionType>> getHandler(
            Class<ExceptionType> exceptionType) {
        return (List<ExceptionHandler<ExceptionType>>) (List<?>) handlers.get(exceptionType);
    }

    public <ExceptionType extends Exception> void handle(ExceptionType exception, Object context) {
        Class<?> myExceptionClass = exception.getClass();

        @SuppressWarnings("unchecked")
        Class<ExceptionType> myCastedClass = (Class<ExceptionType>) myExceptionClass;

        List<ExceptionHandler<ExceptionType>> myHandlerList = getHandler(myCastedClass);

        if (myHandlerList != null && !myHandlerList.isEmpty()) {
            for (ExceptionHandler<ExceptionType> handler : myHandlerList) {
                handler.handle(exception, context);
            }
        } else {
            throw new IllegalArgumentException("No handler is registered for exception type: " + exception.getClass());
        }
    }

}
