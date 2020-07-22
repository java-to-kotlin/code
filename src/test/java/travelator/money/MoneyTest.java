package travelator.money;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static travelator.money.Currencies.*;

public class MoneyTest {
    @Test
    public void representation() {
        assertEquals("17.25 GBP", Money.of("17.25", GBP).toString());
        assertEquals("20.00 EUR", Money.of("20", EUR).toString());
        assertEquals("1.500 JOD", Money.of("1.5", JOD).toString());
        assertEquals("1200 JPY", Money.of("1200", JPY).toString());
    }

    @Test public void summing() {
        final var moneys = List.of(
            Money.of("100.00", GBP),
            Money.of("200.00", EUR),
            Money.of("25.00", GBP),
            Money.of("1500.000", JOD),
            Money.of("300.00", EUR));

        var amountsByCurrency = moneys.stream().collect(Money.sumByCurrency());

        assertEquals(Map.of(
            GBP, Money.of("125.00", GBP),
            EUR, Money.of("500.00", EUR),
            JOD, Money.of("1500.000", JOD)
        ), amountsByCurrency);
    }
}