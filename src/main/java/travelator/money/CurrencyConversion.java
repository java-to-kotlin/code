package travelator.money;

import java.util.Objects;

public class CurrencyConversion {
    private final Money fromMoney; // <1>
    private final Money toMoney;

    // <2>
    public CurrencyConversion(Money fromMoney, Money toMoney) {
        this.fromMoney = fromMoney;
        this.toMoney = toMoney;
    }

    public Money getFromMoney() { // <3>
        return fromMoney;
    }

    public Money getToMoney() { // <3>
        return toMoney;
    }

    @Override
    public boolean equals(Object o) { // <4>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyConversion that = (CurrencyConversion) o;
        return fromMoney.equals(that.fromMoney) &&
            toMoney.equals(that.toMoney);
    }

    @Override
    public int hashCode() { // <4>
        return Objects.hash(fromMoney, toMoney);
    }

    @Override
    public String toString() { // <5>
        return "CurrencyConversion{" +
            "fromMoney=" + fromMoney +
            ", toMoney=" + toMoney +
            '}';
    }
}