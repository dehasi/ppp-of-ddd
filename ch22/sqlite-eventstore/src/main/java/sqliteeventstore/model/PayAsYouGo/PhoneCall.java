package sqliteeventstore.model.PayAsYouGo;

import java.time.LocalDateTime;

public record PhoneCall(
        PhoneNumber numberDialled,
        LocalDateTime callStart,
        Minutes minutes
) {}
