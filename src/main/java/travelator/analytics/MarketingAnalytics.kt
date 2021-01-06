package travelator.analytics

import kotlin.streams.asSequence

class MarketingAnalytics(
    private val eventStore: EventStore
) {
    fun averageNumberOfEventsPerCompletedBooking(
        timeRange: String
    ): Double = eventStore
        .queryAsSequence("type=CompletedBooking&timerange=$timeRange")
        .allEventsInSameInteractions()
        .groupBy(Event::interactionId)
        .values
        .averageBy { it.size }

    private fun Sequence<Event>.allEventsInSameInteractions() =
        flatMap { event ->
            eventStore.queryAsSequence(
                "interactionId=${event.interactionId}"
            )
        }
}

inline fun <T> Collection<T>.averageBy(selector: (T) -> Int): Double =
    sumBy(selector) / size.toDouble()

fun EventStore.queryAsSequence(query: String) =
    this.queryAsStream(query).asSequence()

typealias Event = Map<String, Any?>

val Event.interactionId: String? get() =
    this["interactionId"] as? String