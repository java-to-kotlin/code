package travelator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import travelator.Trip.BookingStatus
import travelator.Trip.BookingStatus.BOOKED
import travelator.Trip.BookingStatus.NOT_BOOKED
import travelator.testing.TimeUtils
import java.time.Instant

class TrackingTests {
    private val trips = InMemoryTrips()
    private val tracking = Tracking(trips)

    @Test
    fun returns_empty_when_no_trip_planned_to_happen_now() {
        assertNull(
            tracking.currentTripFor("cust1", anInstant())
        )
    }

    @Test
    fun returns_single_active_booked_trip() {
        val diwaliTrip = givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED)
        givenATrip("cust1", "Christmas",
            "2020-12-24", "2020-11-26", BOOKED)
        assertEquals(
            diwaliTrip,
            tracking.currentTripFor("cust1",
                diwaliTrip.plannedStartTime.toInstant())
        )
    }

    @Test
    fun returns_only_customers_trip() {
        val diwaliTrip = givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED)
        givenATrip("aDifferentCustomer", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED)
        assertEquals(
            diwaliTrip,
            tracking.currentTripFor("cust1",
                diwaliTrip.plannedStartTime.toInstant())
        )
    }

    @Test
    fun returns_single_booked_trip() {
        val diwaliTrip = givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED)
        givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", NOT_BOOKED)
        assertEquals(
            diwaliTrip,
            tracking.currentTripFor("cust1",
                diwaliTrip.plannedStartTime.toInstant())
        )
    }

    @Test
    fun throws_when_more_than_one_simultaneous_booked_trip() {
        val diwaliTrip = givenATrip("cust1", "Diwali", "" +
            "2020-11-13", "2020-11-15", BOOKED)
        givenATrip("cust1", "Diwali",
            "2020-11-13", "2020-11-15", BOOKED)
        assertThrows(IllegalStateException::class.java) {
            tracking.currentTripFor("cust1",
                diwaliTrip.plannedStartTime.toInstant())
        }
    }

    private fun givenATrip(
        customerId: String,
        name: String,
        startDate: String,
        endDate: String,
        bookingStatus: BookingStatus
    ): Trip {
        val trip = Trip(
            "ignoredId",
            customerId,
            name,
            TimeUtils.zonedDateTime(startDate + "T12:00:00+00:00[Europe/London]"),
            TimeUtils.zonedDateTime(endDate + "T12:00:00+00:00[Europe/London]"),
            bookingStatus
        )
        trips.addTrip(trip)
        return trip
    }
}

private fun anInstant() = Instant.now()