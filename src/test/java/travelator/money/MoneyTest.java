package travelator.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static travelator.money.Currencies.*;

public class MoneyTest {
    @Test
    public void representation() {
        assertEquals("17.25 GBP", Money.of("17.25", GBP).toString());
        assertEquals("20.00 EUR", Money.of("20", EUR).toString());
        assertEquals("1.500 JOD", Money.of("1.5", JOD).toString());
        assertEquals("1200 JPY", Money.of("1200", JPY).toString());
    }

    @Test public void adding() {
        assertEquals(Money.of(275, EUR), Money.of(200, EUR).add(Money.of(75, EUR)));
        assertEquals(Money.of(266, GBP), Money.of(266, GBP).add(Money.zero(GBP)));
    }

    @Test
    public void cannot_add_money_of_different_currencies() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(1, GBP).add(Money.of(2, EUR)));
    }
}