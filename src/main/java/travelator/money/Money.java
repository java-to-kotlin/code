package travelator.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) { // <1>
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, Currency currency) { // <1>
        return new Money(
            amount.setScale(currency.getDefaultFractionDigits()),
            currency);
    }


    public static Money of(String amountStr, Currency currency) { // <2>
        return Money.of(new BigDecimal(amountStr), currency);
    }

    public static Money of(int amount, Currency currency) {
        return Money.of(new BigDecimal(amount), currency);
    }

    public static Money zero(Currency userCurrency) {
        return Money.of(ZERO, userCurrency);
    }


    public BigDecimal getAmount() { // <2>
        return amount;
    }

    public Currency getCurrency() { // <3>
        return currency;
    }

    @Override
    public boolean equals(Object o) { // <3>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) &&
            currency.equals(money.currency);
    }

    @Override
    public int hashCode() { // <3>
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() { // <4>
        return amount.toString() + " " + currency.getCurrencyCode();
    }

    public Money add(Money that) { // <5>
        if (!this.currency.equals(that.currency)) {
            throw new IllegalArgumentException(
                "cannot add Money values of different currencies");
        }

        return new Money(this.amount.add(that.amount), this.currency);
    }
}