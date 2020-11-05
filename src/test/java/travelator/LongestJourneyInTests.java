package travelator;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static travelator.Suffering.longestJourneysIn;

public class LongestJourneyInTests {

    @Test public void returns_empty_list_for_empty_list() {
        assertEquals(
            emptyList(),
            longestJourneysIn(emptyList(), 2)
        );
    }

    @Test public void returns_empty_list_for_0_limit() {
        assertEquals(
            emptyList(),
            longestJourneysIn(List.of(shortJourney), 0)
        );
    }

    @Test public void returns_journeys_sorted() {
        assertEquals(
            List.of(longJourney, mediumJourney, shortJourney),
            longestJourneysIn(List.of(shortJourney, mediumJourney, longJourney), 3)
        );
    }

    @Test public void returns_limit_results() {
        assertEquals(
            List.of(longJourney, mediumJourney),
            longestJourneysIn(List.of(shortJourney, mediumJourney, longJourney), 2)
        );
    }

    @Test public void returns_up_to_limit_results() {
        assertEquals(
            List.of(longJourney, mediumJourney, shortJourney),
            longestJourneysIn(List.of(shortJourney, mediumJourney, longJourney), 4)
        );
    }

    private final ZonedDateTime now = ZonedDateTime.now();
    private Location somewhere = new Location();
    private Location somewhereElse = new Location();

    private final Journey shortJourney = new Journey(somewhere, somewhereElse, now, now.plusHours(1));
    private final Journey mediumJourney = new Journey(somewhere, somewhereElse, now, now.plusHours(2));
    private final Journey longJourney = new Journey(somewhere, somewhereElse, now, now.plusHours(3));
}