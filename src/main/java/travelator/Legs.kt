package travelator

import java.time.Duration
import java.util.*

object Legs {
    @JvmStatic
    fun findLongestLegOver(
        legs: List<Leg>,
        duration: Duration
    ): Optional<Leg> {
        return Optional.ofNullable(longestLegOver(legs, duration))
    }

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

    private fun isLongerThan(leg: Leg, duration: Duration): Boolean {
        return leg.plannedDuration.compareTo(duration) > 0
    }
}