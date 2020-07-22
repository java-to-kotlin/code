package travelator.money

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class ExchangeRatesViaBaseCurrency(
    private val baseCurrency: Currency,
    rates: Map<Currency, BigDecimal>
) : ExchangeRates {

    private val rates = rates.mapValues { (_, v) -> asRate(v) }

    override fun rate(fromCurrency: Currency, toCurrency: Currency): BigDecimal {
        val fromRate = baseRate(fromCurrency)
        val toRate = baseRate(toCurrency)
        return toRate.divide(fromRate, RoundingMode.HALF_EVEN)
    }

    private fun baseRate(c: Currency): BigDecimal {
        return if (c == baseCurrency) asRate(BigDecimal.ONE) else rates[c]!!
    }

    private fun asRate(value: BigDecimal): BigDecimal {
        return value.setScale(6, RoundingMode.UNNECESSARY)
    }
}