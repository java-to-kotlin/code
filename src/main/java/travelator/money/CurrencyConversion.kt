package travelator.money

import java.util.*

class CurrencyConversion(val fromMoney: Money, val toMoney: Money) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CurrencyConversion
        return fromMoney == that.fromMoney && toMoney == that.toMoney
    }

    override fun hashCode(): Int {
        return Objects.hash(fromMoney, toMoney)
    }

    override fun toString(): String {
        return "CurrencyConversion{" +
            "fromMoney=" + fromMoney +
            ", toMoney=" + toMoney +
            '}'
    }
}