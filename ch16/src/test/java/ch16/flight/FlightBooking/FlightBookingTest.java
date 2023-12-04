package ch16.flight.FlightBooking;

import ch16.flight.FlightBooking.FlightBooking.RescheduleRejected;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FlightBookingTest {

    @Test void departure_date_can_be_rescheduled_whilst_pending_confirmation_from_airline() {
        var id = UUID.randomUUID();
        var customerId = UUID.randomUUID();
        var initialDeparture = LocalDate.of(2015, 04, 22);
        var booking = new FlightBooking(id, initialDeparture, customerId);

        var rescheduledDeparture = LocalDate.of(2015, 04, 23);
        booking.reschedule(rescheduledDeparture);

        assertThat(rescheduledDeparture).isEqualTo(booking.departureDate());
    }

    @Test void departure_date_cannot_be_rescheduled_after_booking_confirmed_by_airline() {
        var id = UUID.randomUUID();
        var customerId = UUID.randomUUID();
        var initialDeparture = LocalDate.of(2015, 04, 22);
        var booking = new FlightBooking(id, initialDeparture, customerId);

        booking.confirm();

        var rescheduledDeparture = LocalDate.of(2015, 04, 23);

        assertThatThrownBy(() ->
                booking.reschedule(rescheduledDeparture)
        ).isInstanceOf(RescheduleRejected.class);
    }
}