package ch17.restaurantBooking.model.DoubleDispatchAlternative;

import ch17.restaurantBooking.model.*;
import ch17.restaurantBooking.model.events.BookingConfirmedByCustomer;

import java.util.UUID;

// Entity
public class RestaurantBooking {
    // hard-coded to fetch dependency using IoC container
    private RestaurantNotifier restaurantNotifier = ServiceLocator.<RestaurantNotifier>get();

    public UUID id;
    Restaurant restaurant;
    Customer customer;
    BookingDetails bookingDetails;
    boolean confirmed;

    public void confirmBooking() {
        // ..
        DomainEvents.raise(new BookingConfirmedByCustomer(this));
        // ..
    }

    private static class ServiceLocator {
        static <T> T get() {
            return null;
        }
    }
}
