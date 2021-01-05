package travelator.analytics

import java.util.function.Function
import java.util.stream.Collectors

class MarketingAnalytics(
    private val eventStore: EventStore
) {
    fun averageNumberOfEventsPerCompletedBooking(
        timeRange: String
    ): Double {
        val eventsForSuccessfulBookings = eventStore
            .queryAsStream("type=CompletedBooking&timerange=$timeRange")
            .flatMap { event: Map<String?, Any?> ->
                val interactionId = event["interactionId"] as String?
                eventStore.queryAsStream("interactionId=$interactionId")
            }
        val bookingEventsByInteractionId = eventsForSuccessfulBookings.collect(
            Collectors.groupingBy(
                Function { event: Map<String, Any> ->
                    event["interactionId"] as String?
                }
            )
        )
        val averageNumberOfEventsPerCompletedBooking = bookingEventsByInteractionId
            .values
            .stream()
            .mapToInt { obj: List<Map<String, Any>> -> obj.size }
            .average()
        return averageNumberOfEventsPerCompletedBooking.orElse(Double.NaN)
    }
}