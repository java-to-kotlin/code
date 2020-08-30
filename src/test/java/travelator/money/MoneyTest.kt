package travelator.money

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import travelator.money.Currencies.*

class MoneyTest {
    @Test
    fun representation() {
        assertEquals("17.25 GBP", Money.of("17.25", GBP).toString())
        assertEquals("20.00 EUR", Money.of("20", EUR).toString())
        assertEquals("1.500 JOD", Money.of("1.5", JOD).toString())
        assertEquals("1200 JPY", Money.of("1200", JPY).toString())
    }

    @Test
    fun adding() {
        assertEquals(
            Money(275, EUR),
            Money(200, EUR) + Money(75, EUR)
        )
        assertEquals(
            Money(266, GBP),
            Money(266, GBP) + Money.zero(GBP)
        )
    }

    @Test
    fun cannot_add_money_of_different_currencies() {
        assertThrows(IllegalArgumentException::class.java) {
            Money(1, GBP) + Money(2, EUR)
        }
    }
}