package travelator.itinerary

import travelator.money.ExchangeRates
import travelator.money.Money
import java.util.Currency

class CostSummaryCalculator(
    private val userCurrency: Currency,
    private val exchangeRates: ExchangeRates
) {
    private val currencyTotals = mutableMapOf<Currency, Money>()

    fun summarise(costs: Iterable<Money>): CostSummary {
        val currencyTotals = mutableMapOf<Currency, Money>()
        costs.forEach {
            currencyTotals.merge(it.currency, it, Money::plus)
        }
        val lines = currencyTotals.values
            .sortedBy { it.currency.currencyCode }
            .map { exchangeRates.convert(it, userCurrency) }
        val total = lines
            .map { it.toMoney }
            .fold(Money(0, userCurrency), Money::add)
        return CostSummary(lines, total)
    }

    fun addCost(cost: Money) {
        currencyTotals.merge(cost.currency, cost, Money::add)
    }

    fun summarise(): CostSummary {
        val lines = currencyTotals.values
            .sortedBy { it.currency.currencyCode }
            .map { exchangeRates.convert(it, userCurrency) }

        val total = lines
            .map { it.toMoney }
            .fold(Money.of(0, userCurrency), Money::add)

        return CostSummary(lines, total)
    }

    fun reset() {
        currencyTotals.clear()
    }
}