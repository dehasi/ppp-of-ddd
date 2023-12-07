package ch17.restaurantBooking.model;


import me.dehasi.replacements.exception.NotImplementedException;

import java.util.UUID;

// Domain Service
class RestaurantNotifierImpl implements RestaurantNotifier {
    @Override public boolean notifyBookingConfirmation(Restaurant Restaurant, Customer Customer, UUID Id, BookingDetails BookingDetails) {
        throw new NotImplementedException();
    }
}
