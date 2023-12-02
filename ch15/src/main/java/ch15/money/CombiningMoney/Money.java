package ch15.money.CombiningMoney;

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

    // Java does not support operator overloading
     static Money operatorPlus(Money left, Money right) {
        return new Money(left.value.add(right.value));
    }

     static Money operatorMinus(Money left, Money right) {
        return new Money(left.value.add(right.value));
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(value);
    }
}
