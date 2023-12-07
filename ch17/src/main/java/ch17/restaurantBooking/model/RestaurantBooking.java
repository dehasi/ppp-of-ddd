package ch17.restaurantBooking.model;

import java.util.UUID;

public class RestaurantBooking {
    // see entities chapter for options when injecting services
    private RestaurantNotifier restaurantNotifier = null;

    UUID id;
    Restaurant restaurant;
    Customer customer;
    BookingDetails bookingDetails;
    boolean confirmed;

    // Entity behavior that depends on a Domain Service (see alternative implementations below)
    public void confirmBooking() {
        // ..

        // restaurantNotifier is the Domain Service,
        // but how can it be available in this scope? especially when an ORM is involved?
        confirmed = restaurantNotifier.notifyBookingConfirmation(restaurant, customer, id, bookingDetails);
        // ..
    }

    public void init(RestaurantNotifier restaurantNotifier) {
        this.restaurantNotifier = restaurantNotifier;
    }
    // ..
}
