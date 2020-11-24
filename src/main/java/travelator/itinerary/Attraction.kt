package travelator.itinerary

import travelator.Id
import travelator.Location
import travelator.geo.PointOverlay
import travelator.geo.StandardIcons
import travelator.money.Money

data class Attraction(
    override val id: Id<Attraction>,
    val location: Location,
    val notes: String
) : ItineraryItem {
    override val description get() =
        location.userReadableName

    override val costs get() =
        emptyList<Money>()

    override val mapOverlay get() =
        PointOverlay(
            position = location.position,
            text = description,
            icon = StandardIcons.ATTRACTION,
            id = id
        )

}