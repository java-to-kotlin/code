package travelator

import travelator.Trip.BookingStatus
import java.time.Instant

class Tracking(
    private val trips: Trips
) : ITrackTrips {

    override fun currentTripFor(customerId: String, at: Instant): Trip? {
        val candidates = trips.currentTripsFor(customerId, at)
            .filter { it.bookingStatus == BookingStatus.BOOKED }
        return when (candidates.size) {
            1 -> candidates[0]
            0 -> null
            else -> throw IllegalStateException(
                "Unexpectedly more than one current trip for $customerId"
            )
        }
    }
}