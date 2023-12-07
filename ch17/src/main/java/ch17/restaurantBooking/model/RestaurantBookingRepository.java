package ch17.restaurantBooking.model;

import java.util.UUID;

class RestaurantBookingRepository {

    private PseudoORMSession session;

    public RestaurantBooking get(UUID restaurantBookingId) {
        // first phase of construction handled by ORM
        var booking = session.<RestaurantBooking>load(restaurantBookingId);

        // second phase of construction manually handled
        booking.init(new RestaurantNotifierImpl());
        // alternatively, via setter or field: booking.Notifier = new RestaurantNotifierImpl();
        return booking;
    }

    private interface PseudoORMSession {
        <T> T load(UUID id);
    }
}
