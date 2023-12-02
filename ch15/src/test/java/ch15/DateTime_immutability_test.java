package ch15;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateTime_immutability_test {

    @Test void plusMonths_creates_new_immutable_DateTime() {
        var jan1st = LocalDate.of(2014, 01, 01);
        var feb1st = jan1st.plusMonths(1);

        // first object remains unchanged
        assertThat(LocalDate.of(2014, 01, 01)).isEqualTo(jan1st);

        // second object is a new immutable instance
        assertThat(LocalDate.of(2014, 02, 01)).isEqualTo(feb1st);
    }

    @Test void plusYears_creates_new_immutable_DateTime() {
        var jan2014 = LocalDate.of(2014, 01, 01);
        var jan2015 = jan2014.plusYears(1);
        var jan2016 = jan2015.plusYears(1);

        // first object remains unchanged
        assertThat(LocalDate.of(2014, 01, 01)).isEqualTo(jan2014);

        // second object remains unchanged
        assertThat(LocalDate.of(2015, 01, 01)).isEqualTo(jan2015);

        assertThat(LocalDate.of(2016, 01, 01)).isEqualTo(jan2016);
    }
}
