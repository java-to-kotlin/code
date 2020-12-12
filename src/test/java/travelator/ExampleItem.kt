package travelator

internal data class ExampleItem(
    val name: String,
    override val rating: Double,
    override val price: Int,
    override val relevance: Double
) : HasPrice, HasRating, HasRelevance