package travelator.itinerary

import travelator.money.CurrencyConversion
import travelator.money.ExchangeRates
import travelator.money.Money
import java.util.*

class CostSummaryCalculator(
    private val userCurrency: Currency,
    private val exchangeRates: ExchangeRates
) {
    fun summarise(costs: Iterable<Money>): CostSummary {
        val currencyTotals: List<Money> = costs
            .groupBy { it.currency }
            .values
            .map { moneys -> moneys.reduce(Money::add) }
        val lines: List<CurrencyConversion> = currencyTotals
            .sortedBy { it.currency.currencyCode }
            .map { exchangeRates.convert(it, userCurrency) }
        val total = lines
            .map { it.toMoney }
            .fold(Money(0, userCurrency), Money::add)
        return CostSummary(lines, total)
    }
}