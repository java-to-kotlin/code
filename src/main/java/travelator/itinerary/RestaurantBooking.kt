package travelator.itinerary

import travelator.Id
import travelator.Location
import travelator.geo.PointOverlay
import travelator.geo.StandardIcons
import travelator.money.Money
import java.time.ZonedDateTime

data class RestaurantBooking(
    override val id: Id<RestaurantBooking>,
    val location: Location,
    val time: ZonedDateTime
) : ItineraryItem {
    override val description get() = location.userReadableName

    override val costs get() = emptyList<Money>()

    override val mapOverlay get() =
        PointOverlay(
            id = id,
            position = location.position,
            text = location.userReadableName,
            icon = StandardIcons.RESTAURANT
        )

}