package travelator.itinerary

import travelator.Location
import java.time.Duration
import java.util.*

class Route(
    val journeys: List<Journey>
) {
    fun size(): Int = journeys.size

    operator fun get(index: Int) = journeys[index]

    val arrivesAt: Location
        get() = get(size() - 1).arrivesAt

    val duration: Duration
        get() = Duration.between(
            get(0).departureTime,
            get(size() - 1).arrivalTime
        )

}

fun Route.withJourneyAt(index: Int, replacedBy: Journey): Route {
    val newJourneys = ArrayList(journeys)
    newJourneys[index] = replacedBy
    return Route(newJourneys)
}

val Route.departsFrom: Location
    get() = get(0).departsFrom