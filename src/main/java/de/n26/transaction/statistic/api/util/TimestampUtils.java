package de.n26.transaction.statistic.api.util;

public class TimestampUtils {

    public static long decreaseDate(long timestamp, int minutes) {

        return timestamp-minutes*60*1000;
    }

    public static long generateTimestampUnix() {

        return System.currentTimeMillis();
    }
}
