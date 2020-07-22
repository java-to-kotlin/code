package travelator.money;

import java.math.BigDecimal;
import java.util.Currency;

public interface ExchangeRates {
    BigDecimal rate(Currency fromCurrency, Currency toCurrency);

    default CurrencyConversion convert(Money fromMoney, Currency toCurrency) {
        BigDecimal rate = rate(fromMoney.getCurrency(), toCurrency);
        BigDecimal toAmount = fromMoney.getAmount().multiply(rate);
        Money toMoney = Money.of(toAmount, toCurrency);
        
        return new CurrencyConversion(fromMoney, toMoney);
    }
}