package ch15.money.BasicMoney;

import ch15.ValueObject;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

class Money extends ValueObject<Money> {

    protected final BigDecimal value;

    Money() {
        this(ZERO);
    }

    Money(BigDecimal value) {
        this.value = value;
    }

    Money add(Money money) {
        return new Money(value.add(money.value));
    }

    Money subtract(Money money) {
        return new Money(value.subtract(money.value));
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(value);
    }
}
