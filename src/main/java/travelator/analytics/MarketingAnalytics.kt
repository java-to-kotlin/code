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
        val averageNumberOfEventsPerCompletedBooking = bookingEventsByInteractionId
            .values
            .stream()
            .mapToInt { it.size }
            .average()
        return averageNumberOfEventsPerCompletedBooking.orElse(Double.NaN)
    }
}