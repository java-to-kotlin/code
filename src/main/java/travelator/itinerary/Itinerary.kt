package travelator.itinerary

import travelator.Id
import java.time.Duration

class Itinerary(
    val id: Id<Itinerary>,
    val route: Route
) {

    fun hasJourneyLongerThan(duration: Duration) =
        route.any { it.duration > duration }

}