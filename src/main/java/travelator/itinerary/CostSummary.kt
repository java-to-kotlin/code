package travelator.itinerary

import travelator.money.CurrencyConversion
import travelator.money.Money

class CostSummary(
    val lines: List<CurrencyConversion>,
    total: Money
) {
    val total = total
}