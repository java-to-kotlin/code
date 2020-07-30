package travelator

import java.time.Duration

fun longestLegOver(
    legs: List<Leg>,
    duration: Duration
): Leg? {
    var result: Leg? = null
    for (leg in legs) {
        if (isLongerThan(leg, duration))
            if (result == null ||
                isLongerThan(leg, result.plannedDuration))
                result = leg
    }
    return result
}

private fun isLongerThan(leg: Leg, duration: Duration) =
    leg.plannedDuration.compareTo(duration) > 0