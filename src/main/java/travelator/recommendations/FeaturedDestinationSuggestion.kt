package travelator.recommendations

import travelator.destinations.FeaturedDestination
import travelator.domain.Location

data class FeaturedDestinationSuggestion(
    val routeLocation: Location,
    val suggestion: FeaturedDestination,
    val distanceMeters: Int
)