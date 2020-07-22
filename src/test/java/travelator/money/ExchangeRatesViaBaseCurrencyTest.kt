package travelator.money

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import travelator.money.Money.Companion.of
import java.math.BigDecimal

class ExchangeRatesViaBaseCurrencyTest {
    private val fx: ExchangeRates = ExchangeRatesViaBaseCurrency(
        EUR,
        mapOf(
            GBP to BigDecimal("0.8"),
            JOD to BigDecimal("100")))

    @Test
    fun same_currency() {
        assertEquals(
            Money("125", EUR),
            fx.convert(of("125", EUR), EUR).toMoney)
    }

    @Test
    fun one_foreign_currency() {
        assertConversionBothWays(of("100", EUR), Money("80", GBP))
    }

    @Test
    fun two_foreign_currencies() {
        assertConversionBothWays(of("80", GBP), Money("10000", JOD))
    }

    private fun assertConversionBothWays(m1: Money, m2: Money) {
        assertConversion(m1, m2)
        assertConversion(m2, m1)
    }

    private fun assertConversion(fromMoney: Money, toMoney: Money) {
        assertEquals(toMoney, fx.convert(fromMoney, toMoney.currency).toMoney) {
            fromMoney.currency.toString() + " -> " + toMoney.currency
        }
    }
}