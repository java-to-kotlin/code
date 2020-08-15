package travelator;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static travelator.Trip.BookingStatus.BOOKED;
import static travelator.testing.TimeUtils.zonedDateTime;

public class TripActiveTests {

    private final ZonedDateTime start = zonedDateTime("2020-07-28T12:00:00+01:00[Europe/London]");
    private final ZonedDateTime end = zonedDateTime("2020-07-28T13:00:00+01:00[Europe/London]");
    private final Trip trip = new Trip("id", "customerId", "title", start, end, BOOKED);

    @Test
    public void start_should_be_inclusive() {
        Instant startInstant = start.toInstant();
        assertFalse(trip.isPlannedToBeActiveAt(startInstant.minusMillis(1)));
        assertTrue(trip.isPlannedToBeActiveAt(startInstant));
        assertTrue(trip.isPlannedToBeActiveAt(startInstant.plusMillis(1)));
    }

    @Test
    public void end_should_be_exclusive() {
        Instant endInstant = end.toInstant();
        assertTrue(trip.isPlannedToBeActiveAt(endInstant.minusMillis(1)));
        assertFalse(trip.isPlannedToBeActiveAt(endInstant));
        assertFalse(trip.isPlannedToBeActiveAt(endInstant.plusMillis(1)));
    }
}