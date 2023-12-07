package ch17.restaurantBooking.model.events;


import ch17.restaurantBooking.model.RestaurantBooking;

public class BookingConfirmedByCustomer {

    public RestaurantBooking restaurantBooking;

    public BookingConfirmedByCustomer(RestaurantBooking booking) {
        this.restaurantBooking = booking;
    }

    public ch17.restaurantBooking.model.DoubleDispatchAlternative.RestaurantBooking altRestaurantBooking;

    public BookingConfirmedByCustomer(ch17.restaurantBooking.model.DoubleDispatchAlternative.RestaurantBooking altRestaurantBooking) {
        // TODO: Complete member initialization
        this.altRestaurantBooking = altRestaurantBooking;
    }
}
