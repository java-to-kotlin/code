package travelator

import org.junit.jupiter.api.Test

class ShortlistScenarioTest {
    data class HotelSearchResult(
        val name: String,
        override val relevance: Double,
        override val price: Int,
        override val rating: Double
    ) : HasRelevance, HasPrice, HasRating

    val theGrand = HotelSearchResult(
        name = "The Grand",
        relevance = 1.0,
        price = 300,
        rating = 4.8
    )

    val sheridanImperial = HotelSearchResult(
        name = "Sheridan Imperial",
        relevance = 0.8,
        price = 400,
        rating = 4.9
    )

    val artHotel = HotelSearchResult(
        name = "Art Hotel",
        relevance = 0.6,
        price = 200,
        rating = 3.9
    )

    @Test
    fun scenario() {
        val hotels = shortlistOf(theGrand, sheridanImperial, artHotel)
        val hotelByRelevance = hotels.sortedWith(byRelevance())
        show(hotelByRelevance, "By Relevance")
        val hotelsByPrice = hotels.sortedWith(byPriceLowToHigh())
        show(hotelsByPrice, "By Price (low to high)")

        println("Rejecting: ${hotelsByPrice[0].name}")
        val desirableHotels = removeItemAt(hotelsByPrice, 0)
        show(desirableHotels, "Remaining shortlist")

        val desirableHotelsByRating = desirableHotels.sortedWith(byRating())
        show(desirableHotelsByRating, "By rating")
        val desirableHotelsByValue = desirableHotels.sortedWith(byValue())
        show(desirableHotelsByValue, "By value")
        println("Chosen: ${desirableHotelsByValue[0].name}")
    }

    private fun show(hotels: List<HotelSearchResult>, ordering: String) {
        println("$ordering: ${hotels.map { it.name }.joinToString(", ")}")
    }

    private fun <T> shortlistOf(vararg items: T): List<T> =
        java.util.List.of(*items)
}