package no.nnsn.quakemlwebserviceingestorexecutable.utils;

import java.time.Duration;
import java.time.Instant;

public class TimeLogger {
    public static String getTimeUsed(Instant start, Instant finish, String label) {
        Duration duration = Duration.between(start, finish);
        return TimeLogger.getTimeMessage(duration, label);
    }

    public static String getTimeUsed(Duration duration, String label) {
        return TimeLogger.getTimeMessage(duration, label);
    }

    public static Duration getDuration(Instant start, Instant finish) {
        return Duration.between(start, finish);
    }

    public static Duration getDuration(Instant start, Instant finish, Duration duration) {
        return duration.plus(getDuration(start, finish));
    }

    private static String getTimeMessage(Duration duration, String label) {

        long timeElapsedHour = duration.toHours();
        long timeElapsedMin = duration.toMinutes();
        long timeElapsedSec = duration.getSeconds();
        long timeElapsedMilliSec = duration.toMillis();
        long timeElapsedNanoSec = duration.toNanos();

        long timeMinAfterHour = (timeElapsedMin - (timeElapsedHour * 60));
        long timeSecAfterHour = (timeElapsedSec - (timeMinAfterHour * 60));
        long timeSecAfterMin = (timeElapsedSec - (timeElapsedMin * 60));

        if (timeElapsedHour != 0) {
            return label + timeElapsedHour + " hours and " + timeMinAfterHour + " minutes and " + timeSecAfterHour  + " seconds";
        }
        if (timeElapsedMin != 0) {
            return label + timeElapsedMin + " minutes and " + timeSecAfterMin  + " seconds";
        } else if (timeElapsedSec != 0) {
            return label + timeElapsedSec + " seconds";
        } else if (timeElapsedMilliSec != 0){
            return label + timeElapsedMilliSec + " milli seconds";
        } else {
            return label + timeElapsedNanoSec + " nano seconds";
        }
    }
}
