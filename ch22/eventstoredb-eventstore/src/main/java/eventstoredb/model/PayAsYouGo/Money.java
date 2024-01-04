package eventstoredb.model.PayAsYouGo;

import eventstoredb.infrastructure.ValueObject;

import java.util.List;

public final class Money extends ValueObject<Money> implements Comparable<Money> {

    private double amount;

    public double getAmount() {
        return amount;
    }

    public Money setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Money() {
        // for Hibernate
    }

    public Money(double value) {
        throwExceptionIfNotValid(value);
        this.amount = value;
    }

    public double value() {return amount;}

    public Money add(Money money) {
        return new Money(amount + money.value());
    }

    public Money minus(Money money) {
        return new Money(amount - money.amount);
    }

    public boolean IsGreaterThan(Money money) {
        return this.amount > money.value();
    }

    public boolean isGreaterThanOrEqualTo(Money money) {
        return this.amount > money.value() || this.equals(money);
    }

    public boolean isLessThanOrEqualTo(Money money) {
        return this.amount < money.value() || this.equals(money);
    }


    public Money subtract(Money money) {
        return new Money(this.amount - money.amount);
    }

    public Money multiplyBy(int number) {
        return new Money(this.amount * number);
    }


    private static void throwExceptionIfNotValid(double value) {
        //  Turned off for
        // if ((value * 100) % 1 != 0) // just use BigDecimal in prod, NEVER use double for money
        //     throw new MoreThanTwoDecimalPlacesInMoneyValueException();

        if (value < 0)
            throw new MoneyCannotBeANegativeValueException();
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(amount);
    }

    @Override public int compareTo(Money that) {
        return Double.compare(this.amount, that.amount);
    }

    private static class MoreThanTwoDecimalPlacesInMoneyValueException extends RuntimeException {}

    private static class MoneyCannotBeANegativeValueException extends RuntimeException {}
}
