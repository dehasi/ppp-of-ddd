package ch14.valueobject;

import java.math.BigDecimal;

public class Price {

    private final BigDecimal price;
    private final Currency currency;

    public Price(BigDecimal price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }

    public Price minus(Price price) {
        return new Price(this.price.subtract(price.price), currency);
    }

    public boolean isGreaterThanZero() {
        return false;
    }

    public Currency currency() {
        return currency;
    }
}
