package travelator.itinerary

import travelator.money.Money

val ItineraryItem.costs: List<Money>
    get() = when (this) {
        is Accommodation -> listOf(totalPrice)
        is Attraction -> emptyList()
        is Journey -> listOf(price)
        is RestaurantBooking -> emptyList()
    }