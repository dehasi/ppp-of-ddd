package ch14.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    protected final BigDecimal value;
    private final Currency currency;

    public Money() {
        this(BigDecimal.ZERO, new SterlingCurrency());
    }

    public Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money add(Money toAdd) {
        if (this.currency.equals(toAdd.currency))
            return new Money(value.add(toAdd.value), currency);
        else
            throw new NonMatchingCurrencyException(
                    "You cannot add money with different currencies.");
    }

    public Money minus(Money toDiscount) {
        if (this.currency.equals(toDiscount.currency))
            return new Money(value.subtract(toDiscount.value), currency);
        else
            throw new NonMatchingCurrencyException(
                    "You cannot add money with different currencies.");
    }

    @Override public String toString() {
        return String.format("{%s}", currency.formatForDisplaying(value));
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (!Objects.equals(value, money.value)) return false;
        return Objects.equals(currency, money.currency);
    }

    @Override public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    private static class NonMatchingCurrencyException extends RuntimeException {
        public NonMatchingCurrencyException(String message) {super(message);}
    }
}
