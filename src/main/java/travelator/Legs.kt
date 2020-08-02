package travelator

import java.time.Duration

fun longestLegOver(
    legs: List<Leg>,
    duration: Duration
): Leg? {
    val longestLeg: Leg? = legs.maxByOrNull(Leg::plannedDuration)
    return if (longestLeg != null && longestLeg.plannedDuration > duration)
        longestLeg
    else
        null
}