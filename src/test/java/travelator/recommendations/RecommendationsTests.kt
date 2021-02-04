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
            distanceInMetersBetween::getValue
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
        givenFeaturedDestinationsFor(paris, of())
        assertEquals(
            emptyList<Any>(),
            recommendations.recommendationsFor(setOf(paris))
        )
    }

    @Test
    fun returns_recommendations_for_single_location() {
        givenFeaturedDestinationsFor(paris, of(eiffelTower, louvre))

        givenADistanceFrom(paris, to = eiffelTower, of = 5000)
        givenADistanceFrom(paris, to = louvre, of = 1000)

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
        givenFeaturedDestinationsFor(paris, of(eiffelTower, louvre))
        givenADistanceFrom(paris, to = eiffelTower, of = 5000)
        givenADistanceFrom(paris, to = louvre, of = 1000)

        givenFeaturedDestinationsFor(alton, of(flowerFarm, watercressLine))
        givenADistanceFrom(alton, to = flowerFarm, of = 5300)
        givenADistanceFrom(alton, to = watercressLine, of = 320)

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
        givenFeaturedDestinationsFor(alton, of(flowerFarm, watercressLine))
        givenADistanceFrom(alton, to = flowerFarm, of = 5300)
        givenADistanceFrom(alton, to = watercressLine, of = 320)

        givenFeaturedDestinationsFor(froyle, of(flowerFarm, watercressLine))
        givenADistanceFrom(froyle, to = flowerFarm, of = 0)
        givenADistanceFrom(froyle, to = watercressLine, of = 6300)

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
        of: List<FeaturedDestination>
    ) {
        featuredDestinations[location] = of
    }

    private fun givenADistanceFrom(
        from: Location,
        to: FeaturedDestination,
        of: Int
    ) {
        distanceInMetersBetween[from to to.location] = of
    }

    private fun location(name: String) = Location(Id.of(name), name, name)

    private fun featured(name: String, locationName: String) =
        featured(name, location(locationName))

    private fun featured(name: String, location: Location) =
        FeaturedDestination(name, location)
}

private fun of(vararg destination: FeaturedDestination)
    = destination.toList()

private fun <K1, K2, V> Map<Pair<K1, K2>, V>.getValue(k1: K1, k2: K2) =
    getValue(k1 to k2)
