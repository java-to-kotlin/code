package travelator.money

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun representation() {
        assertEquals("17.25 GBP", Money("17.25", GBP).toString())
        assertEquals("20.00 EUR", Money("20", EUR).toString())
        assertEquals("1.500 JOD", Money("1.5", JOD).toString())
        assertEquals("1200 JPY", Money("1200", JPY).toString())
    }

    @Test
    fun adding() {
        assertEquals(
            Money(275, EUR),
            Money(200, EUR) + Money(75, EUR))
        assertEquals(
            Money(266, GBP),
            Money(266, GBP) + Money.zero(GBP))
    }

    @Test
    fun cannot_add_money_of_different_currencies() {
        assertThrows(IllegalArgumentException::class.java) {
            Money(1, GBP) + Money(2, EUR)
        }
    }
}