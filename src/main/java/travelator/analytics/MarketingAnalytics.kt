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
        return bookingEventsByInteractionId.values.averageBy { it.size }
    }
}

inline fun <T> Collection<T>.averageBy(selector: (T) -> Int): Double =
    sumBy(selector) / size.toDouble()