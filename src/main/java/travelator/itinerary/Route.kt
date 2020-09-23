package travelator.itinerary

typealias Route = List<Journey>

fun Route.costs() = map { it.price }