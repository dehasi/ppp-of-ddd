package ch16.flight.FlightBooking;

import java.time.LocalDate;
import java.util.UUID;

class FlightBooking {

    private boolean confirmed = false;

    private final UUID id;
    private final UUID customerId;
    private LocalDate departureDate;

    public FlightBooking(UUID id, LocalDate departureDate, UUID customerId) {
        if (id == null)
            throw new IdMissing();

        if (departureDate == null)
            throw new DepartureDateMissing();

        if (customerId == null)
            throw new CustomerIdMissing();

        this.id = id;
        this.departureDate = departureDate;
        this.customerId = customerId;
    }


    public void reschedule(LocalDate newDeparture) {
        if (confirmed) throw new RescheduleRejected();

        this.departureDate = newDeparture;
    }

    public void confirm() {
        this.confirmed = true;
    }

    public LocalDate departureDate() {
        return departureDate;
    }

    public static class IdMissing extends RuntimeException {}

    public static class DepartureDateMissing extends RuntimeException {}

    public static class CustomerIdMissing extends RuntimeException {}

    public static class RescheduleRejected extends RuntimeException {}
}
