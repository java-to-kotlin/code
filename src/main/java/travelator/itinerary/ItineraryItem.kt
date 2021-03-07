package travelator.itinerary

import travelator.Id
import travelator.Location
import travelator.geo.*
import travelator.money.Money
import java.time.Duration
import java.time.Period
import java.time.ZonedDateTime

sealed class ItineraryItem {
    abstract val id: Id<ItineraryItem>
    abstract val description: String
    abstract val costs: List<Money>
}

val ItineraryItem.mapOverlay: MapOverlay get() = when (this) {
    is Accommodation -> mapOverlay
    is Attraction -> mapOverlay
    is Journey -> mapOverlay
    is RestaurantBooking -> mapOverlay
}

data class Accommodation(
    override val id: Id<Accommodation>,
    val location: Location,
    val checkInFrom: ZonedDateTime,
    val checkOutBefore: ZonedDateTime,
    val pricePerNight: Money
) : ItineraryItem() {
    val nights = Period.between(checkInFrom.toLocalDate(), checkOutBefore.toLocalDate()).days
    val totalPrice: Money = pricePerNight * nights

    override val description
        get() = "$nights nights at ${location.userReadableName}"
    override val costs
        get() = listOf(totalPrice)
    val mapOverlay
        get() = PointOverlay(
            id = id,
            position = location.position,
            text = location.userReadableName,
            icon = StandardIcons.HOTEL
        )

}

data class Attraction(
    override val id: Id<Attraction>,
    val location: Location,
    val notes: String
) : ItineraryItem() {
    override val description get() =
        location.userReadableName

    override val costs get() =
        emptyList<Money>()

    val mapOverlay get() =
        PointOverlay(
            position = location.position,
            text = description,
            icon = StandardIcons.ATTRACTION,
            id = id
        )

}

data class Journey(
    override val id: Id<Journey>,
    val travelMethod: TravelMethod,
    val departsFrom: Location,
    val departureTime: ZonedDateTime,
    val arrivesAt: Location,
    val arrivalTime: ZonedDateTime,
    val price: Money,
    val path: List<Position> = listOf(departsFrom.position, arrivesAt.position),
) : ItineraryItem() {
    override val description
        get() = "${departsFrom.userReadableName} " +
            "to ${arrivesAt.userReadableName} " +
            "by ${travelMethod.userReadableName}"

    override val costs
        get() = listOf(price)

    val mapOverlay
        get() = OverlayGroup(
            id = id,
            elements = listOf(
                PathOverlay(path, travelMethod.userReadableName),
                PointOverlay(departsFrom.position, departsFrom.userReadableName, StandardIcons.START),
                PointOverlay(arrivesAt.position, arrivesAt.userReadableName, StandardIcons.END)
            )
        )

}

data class RestaurantBooking(
    override val id: Id<RestaurantBooking>,
    val location: Location,
    val time: ZonedDateTime
) : ItineraryItem() {
    override val description get() = location.userReadableName

    override val costs get() = emptyList<Money>()

    val mapOverlay get() =
        PointOverlay(
            id = id,
            position = location.position,
            text = location.userReadableName,
            icon = StandardIcons.RESTAURANT
        )
}