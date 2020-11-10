package travelator

import travelator.Other.SOME_COMPLICATED_RESULT
import travelator.Other.routesFor

object Suffering {
    @JvmStatic
    fun sufferScoreFor(route: List<Journey>): Int =
        sufferScore(
            route.longestJourneys(limit = 3),
            Routes.getDepartsFrom(route)
        )

    @JvmStatic
    fun List<Journey>.longestJourneys(limit: Int): List<Journey> =
        sortedByDescending { it.duration }.take(limit)

    fun routesToShowFor(itineraryId: String?): List<List<Journey>> =
        bearable(routesFor(itineraryId))

    private fun bearable(routes: List<List<Journey>>): List<List<Journey>> =
        routes.filter { sufferScoreFor(it) <= 10 }

    private fun sufferScore(
        longestJourneys: List<Journey>,
        start: Location
    ): Int = SOME_COMPLICATED_RESULT()
}