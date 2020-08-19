package travelator;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Legs {

    public static Optional<Leg> findLongestLegOver(
        List<Leg> legs,
        Duration duration
    ) {
        var longestLeg = legs.stream()
            .max(Comparator.comparing(Leg::getPlannedDuration));
        if (longestLeg.isEmpty()) {
            return Optional.empty();
        } else if (isLongerThan(longestLeg.get(), duration)) {
            return longestLeg;
        } else {
            return Optional.empty();
        }
    }

    private static boolean isLongerThan(Leg leg, Duration duration) {
        return leg.getPlannedDuration().compareTo(duration) > 0;
    }
}