package ch15.money.SelfValidating;

import ch15.ValueObject;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static zeliba.the.TheComparable.the;

class Money extends ValueObject<Money> {

    protected final BigDecimal value;

    Money() {
        this(ZERO);
    }

    Money(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    static void validate(BigDecimal value) {
        if (value.stripTrailingZeros().scale() > 2)
            throw new MoreThanTwoDecimalPlacesInMoneyValueException();

        if (the(value).isLessThan(ZERO))
            throw new MoneyCannotBeANegativeValueException();
    }

    Money add(Money money) {
        return new Money(value.add(money.value));
    }

    static Money create(int amount) {
        if (amount < 0)
            throw new MoneyCannotBeANegativeValueException();

        return new Money(BigDecimal.valueOf(amount));
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(value);
    }

    private static class MoneyCannotBeANegativeValueException extends RuntimeException {}

    private static class MoreThanTwoDecimalPlacesInMoneyValueException extends RuntimeException {}
}
