package travelator.analytics

import kotlin.streams.asSequence

class MarketingAnalytics(
    private val eventStore: EventStore
) {
    fun averageNumberOfEventsPerCompletedBooking(
        timeRange: String
    ): Double {
        val eventsForSuccessfulBookings = eventStore
            .queryAsStream("type=CompletedBooking&timerange=$timeRange")
            .asSequence()
            .flatMap { event ->
                val interactionId = event["interactionId"] as String
                eventStore
                    .queryAsStream("interactionId=$interactionId")
                    .asSequence()
            }
        val bookingEventsByInteractionId = eventsForSuccessfulBookings
            .asSequence()
            .groupBy { event ->
                event["interactionId"] as String
            }
        return bookingEventsByInteractionId.values.averageBy { it.size }
    }
}

inline fun <T> Collection<T>.averageBy(selector: (T) -> Int): Double =
    sumBy(selector) / size.toDouble()