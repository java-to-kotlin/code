package travelator.geo

import travelator.Id
import travelator.itinerary.TravelMethod
import java.net.URI
import java.util.Locale.ROOT

sealed class MapOverlay {
    abstract val id: Id<*>?
}

data class PointOverlay(
    val position: Position,
    val text: String,
    val icon: URI ? = null,
    override val id: Id<*>? = null,
) : MapOverlay()

data class PathOverlay(
    val path: List<Position>,
    val text: String,
    override val id: Id<*>? = null
): MapOverlay()

data class AreaOverlay(
    val perimeter: List<Position>,
    val text: String,
    override val id: Id<*>? = null
) : MapOverlay()

data class OverlayGroup(
    val elements: List<MapOverlay>,
    override val id: Id<*>? = null
) : MapOverlay()

object StandardIcons {
    val HOTEL = URI("urn:icon:hotel")
    val RESTAURANT = URI("urn:icon:hotel")
    val ATTRACTION = URI("urn:icon:attraction")
    val START = URI("urn:icon:start")
    val END = URI("urn:icon:end")

    fun iconFor(travelMethod: TravelMethod): URI =
        URI("urn:icon:travel:${travelMethod.name.toLowerCase(ROOT)}")
}