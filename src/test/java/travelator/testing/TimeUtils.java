package travelator.testing;

import java.time.ZonedDateTime;

public class TimeUtils {
    public static ZonedDateTime zonedDateTime(String s) {
        return ZonedDateTime.parse(s);
    }
}