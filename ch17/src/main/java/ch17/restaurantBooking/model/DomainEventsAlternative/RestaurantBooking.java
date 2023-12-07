package ch17.restaurantBooking.model.DomainEventsAlternative;

import ch17.restaurantBooking.model.BookingDetails;
import ch17.restaurantBooking.model.Customer;
import ch17.restaurantBooking.model.Restaurant;
import ch17.restaurantBooking.model.RestaurantNotifier;

import java.util.UUID;

// Entity
public class RestaurantBooking {
    // see entities chapter for options when injecting services
    private RestaurantNotifier restaurantNotifier = null;

    public UUID id;
    public Restaurant restaurant;
    public Customer customer;
    public BookingDetails bookingDetails;
    public boolean confirmed;

    public void confirmBooking(RestaurantNotifier restaurantNotifier) {
        // ..
        confirmed = restaurantNotifier.notifyBookingConfirmation(restaurant, customer, id, bookingDetails);
        // ..
    }

    // this called Double Dispatch:
    // a service passed to an entity, then the entity is passed into the service.
    public void confirmBookingDD(RestaurantNotifier restaurantNotifier) {
        // ..
        confirmed = restaurantNotifier.notifyBookingConfirmation(this);
        // ..
    }
}
