package travelator.itinerary

fun Iterable<Itinerary>.shortest() =
    minByOrNull {
        it.duration // <1>
    }

fun Route.withoutJourneysBy(travelMethod: TravelMethod) =
    this.filterNot { it.method == travelMethod }

fun Itinerary.withoutJourneysBy(travelMethod: TravelMethod) =
    withTransformedRoute {
        filterNot { it.method == travelMethod }
    }

fun Itinerary.withoutLastJourney() =
    withTransformedRoute { dropLast(1) }