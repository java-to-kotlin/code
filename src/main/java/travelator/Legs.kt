package travelator

import java.time.Duration

fun longestLegOver(
    legs: List<Leg>,
    duration: Duration
): Leg? {
    val longestLeg = legs.maxByOrNull(Leg::plannedDuration) ?:
        return null
    return if (longestLeg.plannedDuration > duration)
        longestLeg
    else
        null
}