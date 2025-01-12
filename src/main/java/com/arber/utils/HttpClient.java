package com.arber.utils;

import com.arber.api.exception.InvalidResponseException;
import com.arber.api.exception.TooManyRequestsException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpClient {
    private static final java.net.http.HttpClient HTTP_CLIENT = java.net.http.HttpClient.newHttpClient();
    private static final ObjectMapper DESERIALIZER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private HttpClient() {}

    public static <T> T executeGet(String anEndpoint, Class<T> aResponseType)
            throws IOException, InterruptedException,
                   TooManyRequestsException, InvalidResponseException
    {
        HttpRequest myRequest = HttpRequest.newBuilder(URI.create(anEndpoint)).GET().build();
        HttpResponse<String> myResponse = HTTP_CLIENT.send(myRequest, HttpResponse.BodyHandlers.ofString());

        if (myResponse.statusCode() == HttpStatus.SC_OK) {
            return DESERIALIZER.readValue(myResponse.body(), aResponseType);
        } else if (myResponse.statusCode() == HttpStatus.SC_TOO_MANY_REQUESTS) {
            throw new TooManyRequestsException(
                    String.format("Rate Limited - Too many requests made to API endpoint '%s', ", anEndpoint));
        } else {
            throw new InvalidResponseException(String.format("Unexpected response code: '%d', response body: '%s'",
                    myResponse.statusCode(), myResponse.body()));
        }
    }

    public static <T> T executeGetWithRetry(String anEndpoint, Class<T> aResponseType, RetryPolicy aRetryPolicy)
            throws IOException, InterruptedException, TooManyRequestsException, InvalidResponseException  {
        int myAttempt = 0;
        long myWaitMillis = aRetryPolicy.initialWaitDurationMillis();

        while (true) {
            try {
                return executeGet(anEndpoint, aResponseType);
            } catch (TooManyRequestsException e) {
                ++myAttempt;

                if (myAttempt > aRetryPolicy.maxRetries()) {
                    throw e;
                }

                if (aRetryPolicy.loggingEnabled()) {
                    LOGGER.warn("Received Code 429 - Too Many Requests. Retrying attempt #{} of {} after {} ms to end point {}",
                            myAttempt, aRetryPolicy.maxRetries(), myWaitMillis, anEndpoint);
                }

                Thread.sleep(myWaitMillis);

                if (aRetryPolicy.exponentialBackoff()) {
                    myWaitMillis *= aRetryPolicy.exponentialBackoffMultiplier();
                }
            }
        }

    }
}
