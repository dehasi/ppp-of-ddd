package ch16.holiday.PushingBehaviorToValueObject;

import java.time.Duration;
import java.time.LocalDate;

import static zeliba.the.TheComparable.the;

class Stay {

    public final LocalDate firstNight;
    public final LocalDate lastNight;

    Stay(LocalDate firstNight, LocalDate lastNight) {
        if (the(firstNight).isGreaterThan(lastNight))
            throw new FirstNightOfStayCannotBeAfterLastNight();

        if (DoesNotMeetMinimumStayDuration(firstNight, lastNight))
            throw new StayDoesNotMeetMinimumDuration();

        this.firstNight = firstNight;
        this.lastNight = lastNight;
    }

    private boolean DoesNotMeetMinimumStayDuration(LocalDate firstNight, LocalDate lastNight) {
        return the(Duration.between(firstNight, lastNight)).isLessThan(Duration.ofDays(3));
    }

    static class FirstNightOfStayCannotBeAfterLastNight extends RuntimeException {}

    static class StayDoesNotMeetMinimumDuration extends RuntimeException {}
}
