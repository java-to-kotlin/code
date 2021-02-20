package travelator.itinerary

import travelator.Id
import java.time.Duration

data class Itinerary(
    val id: Id<Itinerary>,
    val route: Route
) : Route by route {

    fun withTransformedRoute(transform: (Route).() -> Route) =
        copy(route = transform(route))

}

fun Route.hasJourneyLongerThan(duration: Duration) =
    any { it.duration > duration }