package ch16.holiday.HolidayBooking;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class HolidayBookingTest {

    @Test void id_is_an_amalgamation_of_travelerId_and_dates() {
        var travelerId = 44563;
        var firstNight = LocalDate.of(2014, 07, 01);
        var lastNight = LocalDate.of(2014, 07, 14);
        var booked = LocalDate.of(2013, 9, 24);

        var booking = new HolidayBooking(travelerId, firstNight, lastNight, booked);

        // it's changed, because I use a bit different formatter.
        var idForHoliday = "44563-01/07/2014-14/07/2014-24/09/2013";
        assertThat(idForHoliday).isEqualTo(booking.id);
    }
}