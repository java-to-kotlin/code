package travelator.recommendations

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import travelator.Id
import travelator.destinations.FeaturedDestination
import travelator.domain.Location

class RecommendationsTests {
    companion object {
        val distances = mapOf(
            (paris to eiffelTower.location) to 5000,
            (paris to louvre.location) to 1000,
            (alton to flowerFarm.location) to 5300,
            (alton to watercressLine.location) to 320,
            (froyle to flowerFarm.location) to 0,
            (froyle to watercressLine.location) to 6300
        )
    }


    @Test
    fun returns_no_recommendations_when_no_locations() {
        check(
            featuredDestinations = emptyMap(),
            distances = distances,
            recommendations = emptySet(),
            shouldReturn = emptyList()
        )
    }


    @Test
    fun returns_no_recommendations_when_no_featured() {
        check(
            featuredDestinations = emptyMap(),
            distances = distances,
            recommendations = setOf(paris),
            shouldReturn = emptyList()
        )
    }


    @Test
    fun returns_recommendations_for_single_location() {
        check(
            featuredDestinations = mapOf(
                paris to listOf(eiffelTower, louvre),
            ),
            distances = distances,
            recommendations = setOf(paris),
            shouldReturn = listOf(
                FeaturedDestinationSuggestion(paris, louvre, 1000),
                FeaturedDestinationSuggestion(paris, eiffelTower, 5000)
            )
        )
    }


    @Test
    fun returns_recommendations_for_multi_location() {
        check(
            featuredDestinations = mapOf(
                paris to listOf(eiffelTower, louvre),
                alton to listOf(flowerFarm, watercressLine),
            ),
            distances = distances,
            recommendations = setOf(paris, alton),
            shouldReturn = listOf(
                FeaturedDestinationSuggestion(alton, watercressLine, 320),
                FeaturedDestinationSuggestion(paris, louvre, 1000),
                FeaturedDestinationSuggestion(paris, eiffelTower, 5000),
                FeaturedDestinationSuggestion(alton, flowerFarm, 5300)
            )
        )
    }

    @Test
    fun deduplicates_using_smallest_distance() {
        check(
            featuredDestinations = mapOf(
                alton to listOf(flowerFarm, watercressLine),
                froyle to listOf(flowerFarm, watercressLine)
            ),
            distances = distances,
            recommendations = setOf(alton, froyle),
            shouldReturn = listOf(
                FeaturedDestinationSuggestion(froyle, flowerFarm, 0),
                FeaturedDestinationSuggestion(alton, watercressLine, 320)
            )
        )
    }
}

private fun subjectFor(
    featuredDestinations: Map<Location, List<FeaturedDestination>>,
    distances: Map<Pair<Location, Location>, Int>
): Recommendations {
    val destinationsLookup = featuredDestinations.withDefault { emptyList() }
    val distanceLookup = distances.withDefault { -1 }
    return Recommendations(destinationsLookup::getValue, distanceLookup::getValue)
}

private fun check(
    featuredDestinations: Map<Location, List<FeaturedDestination>>,
    distances: Map<Pair<Location, Location>, Int>,
    recommendations: Set<Location>,
    shouldReturn: List<FeaturedDestinationSuggestion>
) {
    assertEquals(
        shouldReturn,
        resultFor(featuredDestinations, distances, recommendations)
    )
}

private fun resultFor(
    featuredDestinations: Map<Location, List<FeaturedDestination>>,
    distances: Map<Pair<Location, Location>, Int>,
    locations: Set<Location>
): List<FeaturedDestinationSuggestion> {
    val subject = subjectFor(featuredDestinations, distances)
    return subject.recommendationsFor(locations)
}

private fun <K1, K2, V> Map<Pair<K1, K2>, V>.getValue(k1: K1, k2: K2) =
    getValue(k1 to k2)

private val paris = location("Paris")
private val louvre = featured("Louvre", "Rue de Rivoli")
private val eiffelTower = featured("Eiffel Tower", "Champ de Mars")
private val alton = location("Alton")
private val froyle = location("Froyle")
private val watercressLine = featured("Watercress Line", "Alton Station")
private val flowerFarm = featured("West End Flower Farm", froyle)

private fun location(name: String) = Location(Id.of(name), name, name)

private fun featured(name: String, locationName: String) =
    featured(name, location(locationName))

private fun featured(name: String, location: Location) =
    FeaturedDestination(name, location)