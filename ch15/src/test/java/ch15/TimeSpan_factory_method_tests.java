package ch15;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.*;
import static org.assertj.core.api.Assertions.assertThat;

// Duration is Java equivalent for TimeSpan
class TimeSpan_factory_method_tests {

    @Test void Duration_factory_methods() {

        var sixDays = Duration.ofDays(6);
        var threeHours = Duration.ofHours(3);
        var twoMillis = Duration.ofMillis(2);

        Duration.of(6, DAYS);
        var sixDaysx = Duration.of(6, DAYS);
        var threeHoursx = Duration.of(3, HOURS);
        var twoMillisx = Duration.of(2, MILLIS);

        assertThat(sixDays).isEqualTo(sixDaysx);
        assertThat(threeHours).isEqualTo(threeHoursx);
        assertThat(twoMillis).isEqualTo(twoMillisx);
    }
}
