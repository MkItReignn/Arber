package com.arber.events.feed;

import java.util.concurrent.TimeUnit;

public record ScheduleConfig(long theInitialDelay, long thePeriod, TimeUnit theUnit) {}
