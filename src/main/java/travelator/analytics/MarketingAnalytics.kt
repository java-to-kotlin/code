package travelator.analytics

import java.util.stream.Collectors.groupingBy

class MarketingAnalytics(
    private val eventStore: EventStore
) {
    fun averageNumberOfEventsPerCompletedBooking(
        timeRange: String
    ): Double {
        val eventsForSuccessfulBookings = eventStore
            .queryAsStream("type=CompletedBooking&timerange=$timeRange")
            .flatMap { event ->
                val interactionId = event["interactionId"] as String
                eventStore.queryAsStream("interactionId=$interactionId")
            }
        val bookingEventsByInteractionId = eventsForSuccessfulBookings.collect(
            groupingBy { event -> event["interactionId"] as String }
        )
        val values = bookingEventsByInteractionId.values
        return averageBy<MutableList<MutableMap<String, Any>>>(values) { it.size }
    }
}

private fun <T : MutableList<MutableMap<String, Any>>> averageBy(
    values: Collection<T>,
    selector: (T) -> Int
): Double {
    return values.sumBy(selector) / values.size.toDouble()
}