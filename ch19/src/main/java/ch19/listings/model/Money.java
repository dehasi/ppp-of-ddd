package ch19.listings.model;

public record Money(double value) /* extends  ValueObject<Money> */ {

    public Money(double value) {
        this.value = value;

    }

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
        if (value % 0.01 != 0)
            throw new MoreThanTwoDecimalPlacesInMoneyValueException();

        if (value < 0)
            throw new MoneyCannotBeANegativeValueException();
    }

    private static class MoreThanTwoDecimalPlacesInMoneyValueException extends RuntimeException {}

    private static class MoneyCannotBeANegativeValueException extends RuntimeException {}
}
