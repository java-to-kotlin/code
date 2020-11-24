package travelator

import travelator.geo.Position

data class Location(
    val id: Id<Location>,
    val localName: String,
    val userReadableName: String,
    val position: Position
)