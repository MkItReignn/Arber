package com.arber.api.exception.theoddsapi;

import com.arber.api.exception.ApiException;

public class TheOddsApiException extends ApiException {
    public TheOddsApiException(String aMessage) {
        super(aMessage);
    }
    public TheOddsApiException(String aMessage, Throwable aCause) {
      super(aMessage, aCause);
    }
}
