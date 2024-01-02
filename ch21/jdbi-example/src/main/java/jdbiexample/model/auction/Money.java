package jdbiexample.model.auction;

import jdbiexample.infrastructure.ValueObject;

import java.util.List;

public final class Money extends ValueObject<Money> implements Comparable<Money> {

    private double value;

    public Money() {
        // for Hibernate
    }

    public Money(double value) {
        throwExceptionIfNotValid(value);
        this.value = value;
    }

    public double value() {return value;}

    public Money add(Money money) {
        return new Money(value + money.value());
    }

    public Money minus(Money money) {
        return new Money(value - money.value);
    }

    public boolean IsGreaterThan(Money money) {
        return this.value > money.value();
    }

    public boolean isGreaterThanOrEqualTo(Money money) {
        return this.value > money.value() || this.equals(money);
    }

    public boolean isLessThanOrEqualTo(Money money) {
        return this.value < money.value() || this.equals(money);
    }

    private static void throwExceptionIfNotValid(double value) {
        if ((value * 100) % 1 != 0) // just use BigDecimal in prod, NEVER use double for money
            throw new MoreThanTwoDecimalPlacesInMoneyValueException();

        if (value < 0)
            throw new MoneyCannotBeANegativeValueException();
    }

    @Override public int compareTo(Money that) {
        return Double.compare(this.value, that.value);
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(value);
    }

    public MoneySnapshot getSnapshot() {
        var snapshot = new MoneySnapshot();
        snapshot.value = this.value;
        return snapshot;
    }


    private static class MoreThanTwoDecimalPlacesInMoneyValueException extends RuntimeException {}

    private static class MoneyCannotBeANegativeValueException extends RuntimeException {}
}
