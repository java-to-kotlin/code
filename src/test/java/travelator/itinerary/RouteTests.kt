package travelator.itinerary

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import travelator.Location
import travelator.itinerary.TravelMethod.*
import java.time.Duration
import java.time.ZonedDateTime

class RouteTests {

    @Test
    fun getDuration() {
        val startTime = someTime()
        val endTime = startTime.plusHours(2)

        val journey1 = Journey(waterloo, alton, startTime, someTime(), RAIL)
        val journey2 = Journey(alton, alresford, someTime(), someTime(), RAIL)
        val journey3 = Journey(alresford, winchester, someTime(), endTime, BUS)
        val route = listOf(journey1, journey2, journey3)

        assertEquals(Duration.ofHours(2), route.duration)
    }

    @Test
    fun replaceJourney() {
        val journey1 = Journey(waterloo, alton, someTime(), someTime(), RAIL)
        val journey2 = Journey(alton, alresford, someTime(), someTime(), CAMEL)
        val journey3 = Journey(alresford, winchester, someTime(), someTime(), BUS)
        val replacement = Journey(alton, alresford, someTime(), someTime(), RAIL)

        val route = listOf(journey1, journey2, journey3)
        assertEquals(
            listOf(journey1, replacement, journey3),
            route.withItemAt(1, replacement)
        )
    }
}

private val waterloo = Location()
private val alton = Location()
private val alresford = Location()
private val winchester = Location()

private fun someTime(): ZonedDateTime {
    return ZonedDateTime.now()
}