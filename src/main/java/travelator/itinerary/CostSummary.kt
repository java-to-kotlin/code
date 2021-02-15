package travelator.itinerary

import travelator.money.CurrencyConversion
import travelator.money.Money
import java.util.Currency

class CostSummary(
    userCurrency: Currency,
    val lines: List<CurrencyConversion>
) {

    var total: Money = Money.of(0, userCurrency)
        private set

    init {
        lines.forEach {
            total += it.toMoney
        }
    }
}