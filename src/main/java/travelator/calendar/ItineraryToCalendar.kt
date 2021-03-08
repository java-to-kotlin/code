package travelator.calendar

import travelator.Id
import travelator.itinerary.*

fun Itinerary.toCalendar() = Calendar(
    id = Id.derivedFrom(this.id),
    events = mapNotNull { it.toCalendarEvent() }
)

fun ItineraryItem.toCalendarEvent(): CalendarEvent? = when (this) {
    is Accommodation -> CalendarEvent(
        start = checkInFrom,
        end = checkOutBefore,
        description = description,
        alarms = listOf(
            Alarm(checkInFrom, "Check in open"),
            Alarm(checkOutBefore.minusHours(1), "Check out")
        )
    )
    is Attraction -> null
    is Journey -> CalendarEvent(
        start = departureTime,
        end = arrivalTime,
        description = description,
        location = departsFrom,
        alarms = listOf(
            Alarm(departureTime.minusHours(1)))
    )
    is RestaurantBooking -> CalendarEvent(
        start = time,
        description= description,
        location = location,
        alarms = listOf(
            Alarm(time.minusHours(1)))
    )
}
