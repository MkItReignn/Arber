package com.arber.utils.errors;

import java.util.function.Function;
import java.util.function.Predicate;

public class ErrorHandler {
    private final ExceptionHandlerRegistry theExceptionHandlerRegistry;

    public ErrorHandler(ExceptionHandlerRegistry anExceptionHandlerRegistry) {
        theExceptionHandlerRegistry = anExceptionHandlerRegistry;
    }

    @FunctionalInterface
    public interface ThrowingFunction<InputType, ReturnType> {
        ReturnType apply(InputType t) throws Exception;
    }

    public <InputType> Predicate<InputType>
    wrapPredicateWithErrorHandling(ThrowingFunction<InputType, Boolean> aThrowingFunction) {
        return input -> {
            try {
                return aThrowingFunction.apply(input);
            } catch (Exception e) {
                theExceptionHandlerRegistry.handle(e, input);
            }
            return false;
        };
    }

    public <InputType, ReturnType>
    Function<InputType, ReturnType> wrapFunctionWithErrorHandling(
            ThrowingFunction<InputType, ReturnType> aThrowingFunction) {
        return input -> {
            try {
                return aThrowingFunction.apply(input);
            } catch (Exception e) {
                theExceptionHandlerRegistry.handle(e, input);
            }
            return null;
        };
    }

}
