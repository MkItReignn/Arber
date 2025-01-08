package com.arber.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.function.Predicate;

public class ErrorHandlingUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingUtils.class);

    @FunctionalInterface
    public interface ThrowingFunction<InputType, ReturnType> {
        ReturnType apply(InputType t) throws Exception;
    }

    public static <InputType, ExceptionType extends Exception> Predicate<InputType> wrapPredicateWithErrorHandling(
            ThrowingFunction<InputType, Boolean> aThrowingFunction,
            ExceptionHandler<ExceptionType> aExceptionHandler,
            Class<ExceptionType> anExceptionType) {
        return input -> {
            try {
                return aThrowingFunction.apply(input);
            } catch (Exception e) {
                if (anExceptionType.isInstance(e)) {
                    aExceptionHandler.handle(anExceptionType.cast(e), input);
                    return false;
                }
            }
        };
    }

    public static <InputType, ReturnType, ExceptionType extends Exception> Function<InputType, ReturnType> wrapFunctionWithErrorHandling(
            ThrowingFunction<InputType, ReturnType> aThrowingFunction,
            ExceptionHandler<ExceptionType> aExceptionHandler,
            Class<ExceptionType> anExceptionType) {
        return input -> {
            try {
                return aThrowingFunction.apply(input);
            } catch (Exception e) {
                if (anExceptionType.isInstance(e)) {
                    aExceptionHandler.handle(anExceptionType.cast(e), input);
                    return null;
                }
            }
        };
    }
}