package ch16.flight.ValidationWithSpecifications;

import java.time.LocalDateTime;
import java.util.UUID;

class FlightBooking {

    Specification<FlightBooking> ffSpec = new FrequentFlyersCanRescheduleAfterBookingConfirmation();
    Specification<FlightBooking> ndSpec = new NoDepartureReschedulingAfterBookingConfirmation();

    private final UUID id;
    private LocalDateTime departureDate;
    private UUID customerId;
    private CustomerStatus customerStatus;
    private boolean confirmed;
    Specification<FlightBooking> spec;

    FlightBooking(UUID Id, LocalDateTime departureDate, UUID customerId, CustomerStatus customerStatus) {
        this.id = Id;
        this.departureDate = departureDate;
        this.customerId = customerId;
        this.customerStatus = customerStatus;
        this.confirmed = false;
        spec = ffSpec.Or(ndSpec);
    }

    // Getters and Setters are omitted

    public void Reschedule(LocalDateTime newDeparture) {
        if (!spec.isSatisfiedBy(this)) throw new RescheduleRejected();

        this.departureDate = newDeparture;
    }

    public void Confirm() {
        this.confirmed = true;
    }

    enum CustomerStatus {
        Regular,
        FrequentFlyer,
        Gold
    }

    private static class FrequentFlyersCanRescheduleAfterBookingConfirmation extends Specification<FlightBooking> {
        @Override boolean isSatisfiedBy(FlightBooking entity) {
            return false;
        }
    }

    private static class NoDepartureReschedulingAfterBookingConfirmation extends Specification<FlightBooking> {
        @Override boolean isSatisfiedBy(FlightBooking entity) {
            return false;
        }
    }

    private static class RescheduleRejected extends RuntimeException {}
}
