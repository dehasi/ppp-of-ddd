package ch17.restaurantBooking.model;

import java.util.UUID;

class RestaurantBookingFactory {
    // Initial object - no ORM to worry about here
    // but will this approach be compatible with an ORM?
    public static RestaurantBooking CreateBooking(Restaurant restaurant, Customer customer, BookingDetails details) {
        var id = UUID.randomUUID();
        var notifier = new RestaurantNotifierImpl();

        // Redesign RestaurantBooking entity to suit this style of construction
        //return new RestaurantBooking(restaurant, customer, details, id, notifier);

        return null;
    }
}
