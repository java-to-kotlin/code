package travelator.testing;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class StoppedClock extends Clock {
    public Instant now = Instant.now();

    @Override
    public Instant instant() {
        return now;
    }

    @Override
    public ZoneId getZone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new UnsupportedOperationException();
    }
}