package travelator

import java.time.Duration

fun longestLegOver(
    legs: List<Leg>,
    duration: Duration
): Leg? {
    val longestLeg = legs.maxByOrNull(Leg::plannedDuration)
    return when {
        longestLeg == null -> null
        longestLeg.plannedDuration > duration -> longestLeg
        else -> null
    }
}