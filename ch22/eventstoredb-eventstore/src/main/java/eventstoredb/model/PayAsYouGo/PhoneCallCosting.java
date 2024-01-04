package eventstoredb.model.PayAsYouGo;

import java.util.Objects;

// Converted from record to class for ImportTestData.FreePhoneCallCosting
public class PhoneCallCosting {
    private Money pricePerMinute;

    public PhoneCallCosting(Money pricePerMinute) {this.pricePerMinute = pricePerMinute;}

    public PhoneCallCosting() {
        this(new Money(0.30));
    }

    public Money determineCostOfCall(Minutes minutes) {
        return minutes.costAt(pricePerMinute);
    }

    public Money getPricePerMinute() {
        return pricePerMinute;
    }

    public PhoneCallCosting setPricePerMinute(Money pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
        return this;
    }

    public Money pricePerMinute() {return pricePerMinute;}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PhoneCallCosting) obj;
        return Objects.equals(this.pricePerMinute, that.pricePerMinute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerMinute);
    }

    @Override
    public String toString() {
        return "PhoneCallCosting[" +
                "pricePerMinute=" + pricePerMinute + ']';
    }

}
