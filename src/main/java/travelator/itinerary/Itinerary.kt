package travelator.itinerary

import travelator.Id
import java.time.Duration

class Itinerary(
    val id: Id<Itinerary>,
    val route: Route
) : Route by route {

}

fun Route.hasJourneyLongerThan(duration: Duration) =
    any { it.duration > duration }