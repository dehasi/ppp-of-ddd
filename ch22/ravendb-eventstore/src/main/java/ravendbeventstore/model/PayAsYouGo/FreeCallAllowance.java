package ravendbeventstore.model.PayAsYouGo;

import ravendbeventstore.infrastructure.Clock;

import java.time.LocalDateTime;

public class FreeCallAllowance {

    // Your allowances will expire after 30 days.  
    // Calls to standard UK mobiles and landlines (01, 02, 03) within the UK.

    public Minutes allowance;
    public final LocalDateTime dateStarted;

    public FreeCallAllowance(Minutes allowance, LocalDateTime dateStarted) {
        this.allowance = allowance;
        this.dateStarted = dateStarted;
    }

    public void subtract(Minutes minutes) {
        allowance = allowance.subtract(minutes);
    }

    public Minutes minutesWhichCanCover(PhoneCall phoneCall, Clock clock) {
        if (allowance.isGreaterOrEqualTo(phoneCall.minutes())) {
            return phoneCall.minutes();
        } else {
            return allowance;
        }
    }

    private boolean stillValid(Clock clock) {
        return dateStarted.plusDays(30).isAfter(clock.time());
    }
}
