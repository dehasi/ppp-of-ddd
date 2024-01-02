package hibernateexample.model.auction;

record Price(Money amount) /* extends ValueObject<Price> */ {

    public boolean canBeExceededBy(Money offer) {
        return offer.isGreaterThanOrEqualTo(bidIncrement());
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
