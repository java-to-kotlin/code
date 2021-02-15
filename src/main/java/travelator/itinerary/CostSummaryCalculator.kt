package travelator.itinerary

import travelator.money.ExchangeRates
import travelator.money.Money
import java.util.*

class CostSummaryCalculator(
    private val userCurrency: Currency,
    private val exchangeRates: ExchangeRates
) {
    private val currencyTotals = mutableMapOf<Currency, Money>()

    fun addCost(cost: Money) {
        currencyTotals.merge(cost.currency, cost, Money::add)
    }

    fun summarise(): CostSummary {
        val conversions = currencyTotals.values.sortedBy {
            it.currency.currencyCode
        }.map { exchangeRates.convert(it, userCurrency) }

        return CostSummary(userCurrency).apply {
            conversions.forEach(this::addLine)
        }
    }

    fun reset() {
        currencyTotals.clear()
    }
}