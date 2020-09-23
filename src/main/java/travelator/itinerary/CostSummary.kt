package travelator.itinerary

import travelator.money.CurrencyConversion
import travelator.money.Money

data class CostSummary(
    val lines: List<CurrencyConversion>,
    val total: Money
)