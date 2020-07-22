package travelator.money;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static travelator.money.Currencies.*;

public class ExchangeRatesViaBaseCurrencyTest {
    private final ExchangeRates fx = new ExchangeRatesViaBaseCurrency(
        EUR,
        Map.entry(GBP, new BigDecimal("0.8")),
        Map.entry(JOD, new BigDecimal("100"))
    );

    @Test
    public void same_currency() throws IOException {
        assertEquals(
            Money.of("125", EUR),
            fx.convert(Money.of("125", EUR), EUR).getToMoney());
    }

    @Test
    public void one_foreign_currency() throws IOException {
        assertConversionBothWays(Money.of("100", EUR), Money.of("80", GBP));
    }

    @Test
    public void two_foreign_currencies() throws IOException {
        assertConversionBothWays(Money.of("80", GBP), Money.of("10000", JOD));
    }

    public void assertConversionBothWays(Money m1, Money m2) throws IOException {
        assertConversion(m1, m2);
        assertConversion(m2, m1);
    }

    public void assertConversion(Money fromMoney, Money toMoney) throws IOException {
        assertEquals(
            toMoney,
            fx.convert(fromMoney, toMoney.getCurrency()).getToMoney(),
            () -> fromMoney.getCurrency() + " -> " + toMoney.getCurrency());
    }
}