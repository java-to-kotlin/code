package travelator.itinerary

import travelator.Id
import travelator.geo.MapOverlay
import travelator.money.Money

interface ItineraryItem {
    val id: Id<ItineraryItem>
    val description: String
    val costs: List<Money>
    val mapOverlay: MapOverlay
}