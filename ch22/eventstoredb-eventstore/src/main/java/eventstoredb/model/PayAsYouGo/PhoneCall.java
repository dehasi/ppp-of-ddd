package eventstoredb.model.PayAsYouGo;

public record PhoneCall(
        PhoneNumber numberDialled,
        String callStart,
        Minutes minutes
) {}
