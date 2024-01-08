package ch.model;

public record Money(double value) {

    public Money multiple_by(int i) {
        return new Money(value * i);
    }

    public Money add(Money money) {
        return new Money(value + money.value());
    }
}
