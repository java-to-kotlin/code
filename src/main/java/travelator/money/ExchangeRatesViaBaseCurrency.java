package travelator.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.UNNECESSARY;

public class ExchangeRatesViaBaseCurrency implements ExchangeRates {
    private final Currency baseCurrency;
    private final Map<Currency, BigDecimal> rates = new HashMap<>();

    @SafeVarargs
    public ExchangeRatesViaBaseCurrency(
        Currency baseCurrency,
        Map.Entry<Currency, BigDecimal>... rates)
    {
        this.baseCurrency = baseCurrency;
        for (var rate : rates) {
            this.rates.put(rate.getKey(), asRate(rate.getValue()));
        }
    }

    @Override
    public BigDecimal rate(Currency fromCurrency, Currency toCurrency) {
        var fromRate = baseRate(fromCurrency);
        var toRate = baseRate(toCurrency);
        return toRate.divide(fromRate, HALF_EVEN);
    }

    private BigDecimal baseRate(Currency c) {
        return c.equals(baseCurrency) ? asRate(ONE) : rates.get(c);
    }

    private BigDecimal asRate(BigDecimal value) {
        return value.setScale(6, UNNECESSARY);
    }
}