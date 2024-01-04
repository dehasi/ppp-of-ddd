package ravendbeventstore.model.PayAsYouGo;

record PayAsYouGoInclusiveMinutesOffer(
        Money spendThreshold,
        Minutes freeMinutes) {

    public PayAsYouGoInclusiveMinutesOffer() {
        this(new Money(10), new Minutes(90));
    }

    public boolean isSatisfiedBy(Money credit) {
        return credit.isGreaterThanOrEqualTo(spendThreshold);
    }
}
