package travelator.analytics

import kotlin.streams.asSequence

class MarketingAnalytics(
    private val eventStore: EventStore
) {
    fun averageNumberOfEventsPerCompletedBooking(
        timeRange: String
    ): Double {
        return eventStore
            .queryAsSequence("type=CompletedBooking&timerange=$timeRange")
            .allEventsInSameInteractions()
            .groupBy { event ->
                event["interactionId"] as String
            }.values
            .averageBy { it.size }
    }

    fun Sequence<Map<String, Any?>>.allEventsInSameInteractions() =
        flatMap { event ->
            val interactionId = event["interactionId"] as String
            eventStore
                .queryAsSequence("interactionId=$interactionId")
        }
}

inline fun <T> Collection<T>.averageBy(selector: (T) -> Int): Double =
    sumBy(selector) / size.toDouble()

fun EventStore.queryAsSequence(query: String) =
    this.queryAsStream(query).asSequence()
