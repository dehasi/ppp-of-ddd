package sqliteeventstore.model.PayAsYouGo;

public record PhoneCallCosting(Money pricePerMinute) {

    public PhoneCallCosting() {
        this(new Money(0.30));
    }

    public Money determineCostOfCall(Minutes minutes) {
        return minutes.costAt(pricePerMinute);
    }
}
