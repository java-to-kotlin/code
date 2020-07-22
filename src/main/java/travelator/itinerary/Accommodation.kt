package travelator.itinerary

import travelator.money.Money

data class Accommodation(
    val totalPrice: Money
    // and other fields...
)

fun Accommodation.addCostsTo(calculator: CostSummaryCalculator) {
    calculator.addCost(totalPrice)
}