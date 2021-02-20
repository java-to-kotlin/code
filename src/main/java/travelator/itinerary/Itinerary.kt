package travelator.itinerary

import travelator.Id
import java.time.Duration

class Itinerary(
    val id: Id<Itinerary>,
    val route: Route
) : Route by route { // <1>

    fun hasJourneyLongerThan(duration: Duration) =
        any { it.duration > duration }

}