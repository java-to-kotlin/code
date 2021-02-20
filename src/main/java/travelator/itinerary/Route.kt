package travelator.itinerary

import travelator.Location
import java.time.Duration

typealias Route = List<Journey>

val Route.departsFrom: Location
    get() = first().departsFrom

val Route.arrivesAt: Location
    get() = last().arrivesAt

val Route.duration: Duration
    get() = Duration.between(
        first().departureTime,
        last().arrivalTime
    )

fun <T> Iterable<T>.withItemAt(index: Int, replacedBy: T): List<T> =
    this.toMutableList().apply {
        this[index] = replacedBy
    }