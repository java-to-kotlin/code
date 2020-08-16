package travelator.money;

import java.util.Objects;

public class CurrencyConversion {
    private final Money fromMoney;
    private final Money toMoney;

    public CurrencyConversion(Money fromMoney, Money toMoney) {
        this.fromMoney = fromMoney;
        this.toMoney = toMoney;
    }

    public Money getFromMoney() {
        return fromMoney;
    }

    public Money getToMoney() {
        return toMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyConversion that = (CurrencyConversion) o;
        return fromMoney.equals(that.fromMoney) &&
            toMoney.equals(that.toMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromMoney, toMoney);
    }

    @Override
    public String toString() {
        return "CurrencyConversion{" +
            "fromMoney=" + fromMoney +
            ", toMoney=" + toMoney +
            '}';
    }
}