package eventstoredb.model.PayAsYouGo;

public record Minutes(int number) {

    public Minutes subtract(Minutes minutes) {
        return new Minutes(number - minutes.number);
    }

    public Money costAt(Money chargePerMinute) {
        return chargePerMinute.multiplyBy(number);
    }

    public boolean isGreaterOrEqualTo(Minutes minutes) {
        return this.number >= minutes.number();
    }
}
