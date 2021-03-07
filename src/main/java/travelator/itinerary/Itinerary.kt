package travelator.itinerary

import travelator.Id
import travelator.geo.OverlayGroup
import travelator.geo.mapOverlay

data class Itinerary(
    val id: Id<Itinerary>,
    val items: List<ItineraryItem>
) : Iterable<ItineraryItem> by items

val Itinerary.mapOverlay
    get() = OverlayGroup(
        id = id,
        elements = items.map { it.mapOverlay })

val Itinerary.costs
    get() = flatMap(ItineraryItem::costs)