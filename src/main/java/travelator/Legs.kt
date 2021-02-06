package travelator

import java.time.Duration
import java.util.*

object Legs {
    @JvmStatic
    fun findLongestLegOver(
        legs: List<Leg>, 
        duration: Duration
    ): Optional<Leg> {
        var result: Leg? = longestLegOver(legs, duration)
        return Optional.ofNullable(result)
    }

    fun longestLegOver(legs: List<Leg>, duration: Duration): Leg? {
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