package travelator.itinerary

import travelator.Location
import java.time.Duration

class Route(
    val journeys: List<Journey>
) : List<Journey> by journeys

val Route.size: Int
    get() = journeys.size

operator fun Route.get(index: Int) = journeys[index]

val Route.departsFrom: Location
    get() = get(0).departsFrom

val Route.arrivesAt: Location
    get() = get(size - 1).arrivesAt

val Route.duration: Duration
    get() = Duration.between(
        get(0).departureTime,
        get(size - 1).arrivalTime
    )

fun Route.withJourneyAt(index: Int, replacedBy: Journey): Route =
    Route(journeys.withItemAt(index, replacedBy))

fun <T> Iterable<T>.withItemAt(index: Int, replacedBy: T): List<T> =
    this.toMutableList().apply {
        this[index] = replacedBy
    }