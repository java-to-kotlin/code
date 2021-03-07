package travelator.itinerary

val ItineraryItem.description: String
    get() = when (this) {
        is Accommodation ->
            "$nights nights at ${location.userReadableName}"
        is Attraction ->
            location.userReadableName
        is Journey ->
            "${departsFrom.userReadableName} " +
                "to ${arrivesAt.userReadableName} " +
                "by ${travelMethod.userReadableName}"
        is RestaurantBooking -> location.userReadableName
    }