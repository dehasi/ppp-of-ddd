package ravendbeventstore.model.PayAsYouGo;

public record PhoneNumber(String number) {

    public boolean isUKLandlineOrMobile() {
        return number.startsWith("+44");
    }

    public boolean isInternational() {
        return !isUKLandlineOrMobile();
    }
}
