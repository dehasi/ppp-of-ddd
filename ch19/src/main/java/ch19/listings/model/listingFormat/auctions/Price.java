package ch19.listings.model.listingFormat.auctions;

import ch19.listings.model.Money;

record Price(Money amount) {
    public boolean canBeExceededBy(Money offer) {
        return amount.value() > offer.value();
    }

    public Money bidIncrement() {
        if (amount.isGreaterThanOrEqualTo(new Money(0.01)) && amount.isLessThanOrEqualTo(new Money(0.99)))
            return amount.add(new Money(0.05));

        if (amount.isGreaterThanOrEqualTo(new Money(1.00)) && amount.isLessThanOrEqualTo(new Money(4.99)))
            return amount.add(new Money(0.20));

        if (amount.isGreaterThanOrEqualTo(new Money(5.00)) && amount.isLessThanOrEqualTo(new Money(14.99)))
            return amount.add(new Money(0.50));

        return amount.add(new Money(1.00));
    }
}
