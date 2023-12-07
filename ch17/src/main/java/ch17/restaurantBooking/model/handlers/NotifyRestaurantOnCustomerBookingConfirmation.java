package ch17.restaurantBooking.model.handlers;

import ch17.restaurantBooking.model.HandleEvents;
import ch17.restaurantBooking.model.RestaurantNotifier;
import ch17.restaurantBooking.model.events.BookingConfirmedByCustomer;

// Domain Event Handler
class NotifyRestaurantOnCustomerBookingConfirmation implements HandleEvents<BookingConfirmedByCustomer> {

    private final RestaurantNotifier restaurantNotifier;

    public NotifyRestaurantOnCustomerBookingConfirmation(RestaurantNotifier restaurantNotifier) {
        this.restaurantNotifier = restaurantNotifier;
    }

    public void Handle(BookingConfirmedByCustomer event) {
        // now the restaurant does not control it's own state as much
        var booking = event.restaurantBooking;

            /*
            Confirmed would need to be made public for this approach to work
            booking.Confirmed = restaurantNotifier.NotifyBookingConfirmation(booking.Restaurant,
            booking.Customer, booking.Id, booking.BookingDetails);
            */
    }
}
