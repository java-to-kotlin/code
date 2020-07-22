package travelator.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) { // <1>
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(String amountStr, Currency currency) { // <2>
        return Money.of(new BigDecimal(amountStr), currency);
    }

    public static Money of(int amount, Currency currency) { // <2>
        return Money.of(new BigDecimal(amount), currency);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(
            amount.setScale(currency.getDefaultFractionDigits()),
            currency);
    }

    public static Money zero(Currency userCurrency) {
        return Money.of(ZERO, userCurrency);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money that) {
        if (!this.currency.equals(that.currency)) {
            throw new IllegalArgumentException("cannot add Money values of different currencies");
        }

        return Money.of(this.amount.add(that.amount), this.currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) &&
            currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount.toString() + " " + currency.getCurrencyCode();
    }

    public static Collector<Money, ?, Map<Currency, Money>> sumByCurrency() {
        return Collectors.toMap(
            Money::getCurrency,
            Function.identity(),
            Money::add);
    }
}