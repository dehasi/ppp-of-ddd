package ch17.restaurantBooking.model;

import ch17.restaurantBooking.model.DomainEventsAlternative.RestaurantBooking;

import java.util.UUID;

public interface RestaurantNotifier {
    boolean notifyBookingConfirmation(Restaurant Restaurant, Customer Customer, UUID Id, BookingDetails BookingDetails);

    default boolean notifyBookingConfirmation(RestaurantBooking restaurantBooking) {return false;}
}
