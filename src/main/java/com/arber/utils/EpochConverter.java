package com.arber.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EpochConverter {
    public static long toEpochMilliseconds(String anIsoDateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(anIsoDateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return zonedDateTime.toInstant().toEpochMilli();
    }
}