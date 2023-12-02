package ch15.examples;

// value object
public record Money(int amount, Currency currency) {

    enum Currency {
        Dollars,
        Pounds,
    }
}
