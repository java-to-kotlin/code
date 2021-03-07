package travelator.calendar

import travelator.Id
import travelator.geo.StandardIcons
import travelator.itinerary.*
import java.time.Duration

fun Itinerary.toCalendar() = Calendar(
    id = Id.derivedFrom(id),
    events = mapNotNull { it.toCalendarEvent() }
)

fun ItineraryItem.toCalendarEvent(): CalendarEvent? = when (this) {
    is Accommodation -> CalendarEvent(
        start = checkInFrom,
        end = checkOutBefore,
        description = "$nights nights at ${location.userReadableName}",
        icon = StandardIcons.HOTEL,
        alarms = listOf(
            Alarm(checkInFrom, "Check in open"),
            Alarm(checkOutBefore.minusHours(1), "Check out")
        )
    )
    is Attraction -> null
    is Journey -> CalendarEvent(
        start = departureTime,
        end = arrivalTime,
        description = "${departsFrom.userReadableName} " +
            "to ${arrivesAt.userReadableName} " +
            "by ${travelMethod.userReadableName}",
        icon = StandardIcons.iconFor(travelMethod),
        location = departsFrom,
        alarms = listOf(
            Alarm(departureTime.minusHours(1))
        )
    )
    is RestaurantBooking -> CalendarEvent(
        start = time,
        end = time + Duration.ofHours(2),
        description = location.userReadableName,
        icon = StandardIcons.RESTAURANT,
        location = location,
        alarms = listOf(
            Alarm(time.minusHours(1))
        )
    )
}
