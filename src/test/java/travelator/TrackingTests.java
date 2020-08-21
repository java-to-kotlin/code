package travelator;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static travelator.Trip.BookingStatus.BOOKED;
import static travelator.Trip.BookingStatus.NOT_BOOKED;
import static travelator.testing.TimeUtils.zonedDateTime;

public class TrackingTests {

    final InMemoryTrips trips = new InMemoryTrips();
    final Tracking tracking = new Tracking(trips);

    @Test
    public void returns_empty_when_no_trip_planned_to_happen_now() {
        assertEquals(
            Optional.empty(),
            tracking.currentTripFor("cust1", anInstant())
        );
    }

    @Test
    public void returns_single_active_booked_trip() {
        var diwaliTrip = givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED);
        givenATrip("cust1", "Christmas",
            "2020-12-24", "2020-11-26", BOOKED);

        assertEquals(
            Optional.of(diwaliTrip),
            tracking.currentTripFor("cust1",
                diwaliTrip.getPlannedStartTime().toInstant())
        );
    }

    @Test
    public void returns_only_customers_trip() {
        var diwaliTrip = givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED);
        givenATrip("aDifferentCustomer", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED);

        assertEquals(
            Optional.of(diwaliTrip),
            tracking.currentTripFor("cust1",
                diwaliTrip.getPlannedStartTime().toInstant())
        );
    }

    @Test
    public void returns_single_booked_trip() {
        var diwaliTrip = givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED);
        givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", NOT_BOOKED);

        assertEquals(
            Optional.of(diwaliTrip),
            tracking.currentTripFor("cust1",
                diwaliTrip.getPlannedStartTime().toInstant())
        );
    }

    @Test
    public void throws_when_more_than_one_simultaneous_booked_trip() {
        var diwaliTrip = givenATrip("cust1", "Diwali", "" +
            "2020-11-13", "2020-11-15", BOOKED);
        givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED);

        assertThrows(IllegalStateException.class,
            () -> tracking.currentTripFor("cust1",
                diwaliTrip.getPlannedStartTime().toInstant())
        );
    }

    private Trip givenATrip(
        String customerId,
        String name,
        String startDate,
        String endDate,
        Trip.BookingStatus bookingStatus
    ) {
        var trip = new Trip(
            "ignoredId",
            customerId,
            name,
            zonedDateTime(startDate + "T12:00:00+00:00[Europe/London]"),
            zonedDateTime(endDate + "T12:00:00+00:00[Europe/London]"),
            bookingStatus
        );
        trips.addTrip(trip);
        return trip;
    }

    private Instant anInstant() {
        return Instant.now();
    }
}