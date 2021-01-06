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
        return averageBy(values) { it.size }
    }
}

private fun averageBy(
    values: Collection<MutableList<MutableMap<String, Any>>>,
    selector: (MutableList<MutableMap<String, Any>>) -> Int
): Double {
    return values.sumBy(selector) / values.size.toDouble()
}