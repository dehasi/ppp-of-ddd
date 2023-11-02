package me.dehasi.ch05.domainModel.model;

import java.math.BigDecimal;
import java.util.Objects;

class Price {

    Money amount;

    Price(Money amount) {
        this.amount = Objects.requireNonNull(amount, "amount cannot be null");
    }

    Money bidIncrement() {
        if (amount.isGreaterThanOrEqualTo(new Money(new BigDecimal("0.01"))) && amount.isLessThanOrEqualTo(new Money(new BigDecimal("0.99"))))
            return amount.add(new Money(new BigDecimal("0.05")));

        if (amount.isGreaterThanOrEqualTo(new Money(new BigDecimal("1.00"))) && amount.isLessThanOrEqualTo(new Money(new BigDecimal("4.99"))))
            return amount.add(new Money(new BigDecimal("0.20")));

        if (amount.isGreaterThanOrEqualTo(new Money(new BigDecimal("5.00"))) && amount.isLessThanOrEqualTo(new Money(new BigDecimal("14.99"))))
            return amount.add(new Money(new BigDecimal("0.50")));

        return amount.add(new Money(new BigDecimal("1.00")));
    }

    boolean canBeExceededBy(Money offer) {
        return offer.isGreaterThanOrEqualTo(bidIncrement());
    }
}
