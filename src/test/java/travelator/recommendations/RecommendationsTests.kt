package travelator.recommendations

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import travelator.Id
import travelator.destinations.FeaturedDestination
import travelator.domain.Location

class RecommendationsTests {

    private val featuredDestinations =
        mutableMapOf<Location, List<FeaturedDestination>>()
            .withDefault { emptyList() }
    private val distanceInMetersBetween =
        mutableMapOf<Pair<Location, Location>, Int>()
            .withDefault { -1 }

    private val recommendations =
        Recommendations(
            featuredDestinations::getValue,
            { l1, l2 -> distanceInMetersBetween.getValue(l1 to l2) }
        )
    private val paris = location("Paris")
    private val louvre = featured("Louvre", "Rue de Rivoli")
    private val eiffelTower = featured("Eiffel Tower", "Champ de Mars")
    private val alton = location("Alton")
    private val froyle = location("Froyle")
    private val watercressLine = featured("Watercress Line", "Alton Station")
    private val flowerFarm = featured("West End Flower Farm", froyle)

    @Test
    fun returns_no_recommendations_when_no_locations() {
        assertEquals(
            emptyList<Any>(),
            recommendations.recommendationsFor(emptySet())
        )
    }

    @Test
    fun returns_no_recommendations_when_no_featured() {
        givenFeaturedDestinationsFor(paris)
        assertEquals(
            emptyList<Any>(),
            recommendations.recommendationsFor(setOf(paris))
        )
    }

    @Test
    fun returns_recommendations_for_single_location() {
        givenFeaturedDestinationsFor(
            paris,
            eiffelTower,
            louvre
        )
        givenADistanceFrom(paris, eiffelTower, 5000)
        givenADistanceFrom(paris, louvre, 1000)
        assertEquals(
            listOf(
                FeaturedDestinationSuggestion(paris, louvre, 1000),
                FeaturedDestinationSuggestion(paris, eiffelTower, 5000)
            ),
            recommendations.recommendationsFor(setOf(paris))
        )
    }

    @Test
    fun returns_recommendations_for_multi_location() {
        givenFeaturedDestinationsFor(
            paris,
            eiffelTower, louvre
        )
        givenFeaturedDestinationsFor(
            alton,
            flowerFarm, watercressLine
        )
        givenADistanceFrom(paris, eiffelTower, 5000)
        givenADistanceFrom(paris, louvre, 1000)
        givenADistanceFrom(alton, flowerFarm, 5300)
        givenADistanceFrom(alton, watercressLine, 320)
        assertEquals(
            listOf(
                FeaturedDestinationSuggestion(alton, watercressLine, 320),
                FeaturedDestinationSuggestion(paris, louvre, 1000),
                FeaturedDestinationSuggestion(paris, eiffelTower, 5000),
                FeaturedDestinationSuggestion(alton, flowerFarm, 5300)
            ),
            recommendations.recommendationsFor(setOf(paris, alton))
        )
    }

    @Test
    fun deduplicates_using_smallest_distance() {
        givenFeaturedDestinationsFor(
            alton,
            flowerFarm, watercressLine
        )
        givenFeaturedDestinationsFor(
            froyle,
            flowerFarm, watercressLine
        )
        givenADistanceFrom(alton, flowerFarm, 5300)
        givenADistanceFrom(alton, watercressLine, 320)
        givenADistanceFrom(froyle, flowerFarm, 0)
        givenADistanceFrom(froyle, watercressLine, 6300)
        assertEquals(
            listOf(
                FeaturedDestinationSuggestion(froyle, flowerFarm, 0),
                FeaturedDestinationSuggestion(alton, watercressLine, 320)
            ),
            recommendations.recommendationsFor(setOf(alton, froyle))
        )
    }

    private fun givenFeaturedDestinationsFor(
        location: Location,
        vararg destinations: FeaturedDestination
    ) {
        featuredDestinations[location] = destinations.toList()
    }

    private fun givenADistanceFrom(
        location: Location,
        destination: FeaturedDestination,
        distanceInMeters: Int
    ) {
        distanceInMetersBetween[location to destination.location] =
            distanceInMeters
    }

    private fun location(name: String) = Location(Id.of(name), name, name)

    private fun featured(name: String, locationName: String) =
        featured(name, location(locationName))

    private fun featured(name: String, location: Location) =
        FeaturedDestination(name, location)
}