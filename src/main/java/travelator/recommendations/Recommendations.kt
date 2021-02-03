package travelator.recommendations

import travelator.destinations.FeaturedDestination
import travelator.domain.DistanceCalculator
import travelator.domain.Location

class Recommendations(
    private val distanceCalculator: DistanceCalculator,
    private val destinationFinder: (Location) -> List<FeaturedDestination>
) {

    fun recommendationsFor(
        journey: Set<Location>
    ): List<FeaturedDestinationSuggestion> =
        journey
            .flatMap { location -> recommendationsFor(location) }
            .deduplicated()
            .sortedBy { it.distanceMeters }

    fun recommendationsFor(
        location: Location
    ): List<FeaturedDestinationSuggestion> =
        destinationFinder(location) // <2>
            .map { featuredDestination ->
                FeaturedDestinationSuggestion(
                    location,
                    featuredDestination,
                    distanceCalculator.distanceInMetersBetween(
                        location,
                        featuredDestination.location
                    )
                )
            }
}


private fun List<FeaturedDestinationSuggestion>.deduplicated() =
    groupBy { it.suggestion }
        .values
        .map { suggestionsWithSameDestination ->
            suggestionsWithSameDestination.closestToJourneyLocation()
        }

private fun List<FeaturedDestinationSuggestion>.closestToJourneyLocation() =
    minByOrNull { it.distanceMeters } ?: error("Unexpected empty group")