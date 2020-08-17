package travelator

import java.time.Instant

interface ITrackTrips {
    fun currentTripFor(customerId: String, at: Instant): Trip?
}