package travelator.recommendations;

import org.junit.jupiter.api.Test;
import travelator.Id;
import travelator.destinations.FeaturedDestination;
import travelator.destinations.FeaturedDestinations;
import travelator.domain.DistanceCalculator;
import travelator.domain.Location;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecommendationsTests {

    private final DistanceCalculator distanceCalculator =
        mock(DistanceCalculator.class);
    private final FeaturedDestinations featuredDestinations =
        mock(FeaturedDestinations.class);
    private final Recommendations recommendations = new Recommendations(
        featuredDestinations,
        distanceCalculator
    );
    private final Location paris = location("Paris");
    private final FeaturedDestination louvre = featured("Louvre", "Rue de Rivoli");
    private final FeaturedDestination eiffelTower = featured("Eiffel Tower", "Champ de Mars");

    private final Location alton = location("Alton");
    private final Location froyle = location("Froyle");
    private final FeaturedDestination watercressLine = featured("Watercress Line", "Alton Station");
    private final FeaturedDestination flowerFarm = featured("West End Flower Farm", froyle);

    @Test
    public void returns_no_recommendations_when_no_locations() {
        assertEquals(
            emptyList(),
            recommendations.recommendationsFor(emptySet())
        );
    }

    @Test
    public void returns_no_recommendations_when_no_featured() {
        givenFeaturedDestinationsFor(paris, emptyList());
        assertEquals(
            emptyList(),
            recommendations.recommendationsFor(Set.of(paris))
        );
    }

    @Test
    public void returns_recommendations_for_single_location() {
        givenFeaturedDestinationsFor(paris,
            List.of(
                eiffelTower,
                louvre
            ));
        givenADistanceBetween(paris, eiffelTower, 5000);
        givenADistanceBetween(paris, louvre, 1000);

        assertEquals(
            List.of(
                new FeaturedDestinationSuggestion(paris, louvre, 1000),
                new FeaturedDestinationSuggestion(paris, eiffelTower, 5000)
            ),
            recommendations.recommendationsFor(Set.of(paris))
        );
    }

    @Test
    public void returns_recommendations_for_multi_location() {
        when(featuredDestinations.findCloseTo(paris))
            .thenReturn(List.of(
                eiffelTower,
                louvre
            ));
        when(distanceCalculator.distanceInMetersBetween(
            paris, eiffelTower.getLocation())
        ).thenReturn(5000);
        when(distanceCalculator.distanceInMetersBetween(
            paris, louvre.getLocation())
        ).thenReturn(1000);

        when(featuredDestinations.findCloseTo(alton))
            .thenReturn(List.of(
                flowerFarm,
                watercressLine
            ));
        when(distanceCalculator.distanceInMetersBetween(
            alton, flowerFarm.getLocation())
        ).thenReturn(5300);
        when(distanceCalculator.distanceInMetersBetween(
            alton, watercressLine.getLocation())
        ).thenReturn(320);

        assertEquals(
            List.of(
                new FeaturedDestinationSuggestion(alton, watercressLine, 320),
                new FeaturedDestinationSuggestion(paris, louvre, 1000),
                new FeaturedDestinationSuggestion(paris, eiffelTower, 5000),
                new FeaturedDestinationSuggestion(alton, flowerFarm, 5300)
            ),
            recommendations.recommendationsFor(Set.of(paris, alton))
        );
    }

    @Test
    public void deduplicates_using_smallest_distance() {
        givenFeaturedDestinationsFor(alton,
            List.of(
                flowerFarm,
                watercressLine
            ));
        givenADistanceBetween(alton, flowerFarm, 5300);
        givenADistanceBetween(alton, watercressLine, 320);

        givenFeaturedDestinationsFor(froyle,
            List.of(
                flowerFarm,
                watercressLine
            ));
        givenADistanceBetween(froyle, flowerFarm, 0);
        givenADistanceBetween(froyle, watercressLine, 6300);

        assertEquals(
            List.of(
                new FeaturedDestinationSuggestion(froyle, flowerFarm, 0),
                new FeaturedDestinationSuggestion(alton, watercressLine, 320)
            ),
            recommendations.recommendationsFor(Set.of(alton, froyle))
        );
    }

    private Location location(String name) {
        return new Location(Id.of(name), name, name);
    }

    private FeaturedDestination featured(String name, String locationName) {
        return featured(name, location(locationName));
    }

    private FeaturedDestination featured(String name, Location location) {
        return new FeaturedDestination(name, location);
    }

    private void givenFeaturedDestinationsFor(Location location, List<FeaturedDestination> result) {
        when(featuredDestinations.findCloseTo(location))
            .thenReturn(result);
    }

    private void givenADistanceBetween(Location location, FeaturedDestination destination, int result) {
        when(distanceCalculator.distanceInMetersBetween(location, destination.getLocation()))
            .thenReturn(result);
    }
}