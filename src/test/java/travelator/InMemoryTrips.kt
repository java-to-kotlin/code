package travelator

import java.time.Instant

class InMemoryTrips : Trips {
    private val trips: MutableMap<String, MutableSet<Trip>> = mutableMapOf()

    fun addTrip(trip: Trip) {
        val existingTrips = trips.getOrDefault(trip.customerId, mutableSetOf())
        existingTrips.add(trip)
        trips[trip.customerId] = existingTrips
    }

    override fun tripsFor(customerId: String) =
        trips.getOrDefault(customerId, emptySet<Trip>())

    override fun currentTripsFor(customerId: String, at: Instant): Set<Trip> =
        tripsFor(customerId)
            .filter { it.isPlannedToBeActiveAt(at) }
            .toSet()
}