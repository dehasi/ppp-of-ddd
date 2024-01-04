package eventstoredb.model.PayAsYouGo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record PhoneNumber(String number) {

    @JsonIgnore
    public boolean isUKLandlineOrMobile() {
        return number.startsWith("+44");
    }

    @JsonIgnore
    public boolean isInternational() {
        return !isUKLandlineOrMobile();
    }
}
