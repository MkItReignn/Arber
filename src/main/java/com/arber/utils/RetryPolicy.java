package com.arber.utils;

public final class RetryPolicy {
    private final int theMaxRetries;
    private final long theInitialWaitDurationMillis;
    private long theExponentialBackoffMultiplier = 2;
    private final boolean theExponentialBackoff;
    private final boolean theIsLoggingEnabled;

    private RetryPolicy(int aMaxRetries,
                        long anInitialWaitDurationMillis,
                        long anExponentialBackoffMultiplier,
                        boolean anExponentialBackoff,
                        boolean aIsLoggingEnabled) {
        theMaxRetries = aMaxRetries;
        theInitialWaitDurationMillis = anInitialWaitDurationMillis;
        theExponentialBackoffMultiplier = anExponentialBackoffMultiplier;
        theExponentialBackoff = anExponentialBackoff;
        theIsLoggingEnabled = aIsLoggingEnabled;
    }

    public int maxRetries() {
        return theMaxRetries;
    }

    public long initialWaitDurationMillis() {
        return theInitialWaitDurationMillis;
    }

    public long exponentialBackoffMultiplier() {
        return theExponentialBackoffMultiplier;
    }

    public boolean exponentialBackoff() {
        return theExponentialBackoff;
    }

    public boolean loggingEnabled() {
        return theIsLoggingEnabled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int theMaxRetries = 3;
        private long theInitialWaitDurationMillis = 1000;
        private long theExponentialBackoffMultiplier = 2;
        private boolean theExponentialBackoff = false;
        private boolean theLoggingEnabled = false;

        private Builder() { }

        public Builder withMaxRetries(int aMaxRetries) {
            if (aMaxRetries < 0) {
                throw new IllegalArgumentException("maxRetries cannot be negative");
            }
            theMaxRetries = aMaxRetries;
            return this;
        }

        public Builder withWaitDurationMillis(long aWaitMillis) {
            if (aWaitMillis < 0) {
                throw new IllegalArgumentException("waitMillis cannot be negative");
            }
            theInitialWaitDurationMillis = aWaitMillis;
            return this;
        }

        public Builder withExponentialBackoffMultiplier(long aMultiplier) {
            if (aMultiplier < 1) {
                throw new IllegalArgumentException("multiplier cannot be negative");
            }
            theExponentialBackoffMultiplier = aMultiplier;
            return this;
        }


        public Builder withExponentialBackoff(boolean aUseExponentialBackoff) {
            theExponentialBackoff = aUseExponentialBackoff;
            return this;
        }

        public Builder withLoggingEnabled(boolean aIsLoggingEnabled) {
            theLoggingEnabled = aIsLoggingEnabled;
            return this;
        }

        public RetryPolicy build() {
            return new RetryPolicy(
                    theMaxRetries,
                    theInitialWaitDurationMillis,
                    theExponentialBackoffMultiplier,
                    theExponentialBackoff,
                    theLoggingEnabled
            );
        }
    }
}