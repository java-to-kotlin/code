package travelator.itinerary

import travelator.money.CurrencyConversion
import travelator.money.Money
import java.util.*

class CostSummary(userCurrency: Currency) {
    private val _lines = mutableListOf<CurrencyConversion>()

    var total: Money = Money.of(0, userCurrency)
        private set

    val lines: List<CurrencyConversion>
        get() = _lines.toList()

    fun addLine(line: CurrencyConversion) {
        _lines.add(line)
        total += line.toMoney
    }
}