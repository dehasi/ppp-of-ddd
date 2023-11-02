package me.dehasi.ch05.domainModel.model;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class Money {

    protected BigDecimal value = ZERO;

    Money() {}

    public Money(BigDecimal value) {
        ThrowExceptionIfNotValid(value);

        this.value = value;
    }

    private void ThrowExceptionIfNotValid(BigDecimal value) {
        if (value.stripTrailingZeros().scale() > 2)
            throw new IllegalStateException("There cannot be more than two BigDecimal places.");

        if (value.compareTo(ZERO) < 0)
            throw new IllegalStateException("Money cannot be a negative value.");
    }

    Money add(Money money) {
        return new Money(value.add(money.value));
    }

    boolean isGreaterThan(Money money) {
        return this.value.compareTo(money.value) > 0;
    }

    boolean isGreaterThanOrEqualTo(Money money) {
        return this.value.compareTo(money.value) > 0 || this.equals(money);
    }

    boolean isLessThanOrEqualTo(Money money) {
        return this.value.compareTo(money.value) < 0 || this.equals(money);
    }

    @Override public String toString() {
        return String.format("{%s}", value);
    }
}