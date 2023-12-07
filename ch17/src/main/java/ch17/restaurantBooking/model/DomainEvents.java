package ch17.restaurantBooking.model;

import ch17.restaurantBooking.model.events.BookingConfirmedByCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/// Domain Events class from http://www.udidahan.com/2008/08/25/domain-events-take-2/
public class DomainEvents {

    // Delegate = Consumer
    // Action = Supplier
    // [ThreadStatic] = ThreadLocal.withInitial(ArrayList::new);
    // private static List<Delegate> _actions;
    // In the book DomainEvents is a generic class, for the simplicity I made it only for BookingConfirmedByCustomer.
    private static final List<Consumer<BookingConfirmedByCustomer>> actions = new ArrayList<>();

    public static void raise(BookingConfirmedByCustomer eventArgs) {
        for (Consumer<BookingConfirmedByCustomer> action : actions) {
            action.accept(eventArgs);
        }
    }

    public static AutoCloseable register(Consumer<BookingConfirmedByCustomer> callback) {
        actions.add(callback);

        return new DomainEventRegistrationRemover(() -> actions.remove(callback));
    }


    private static final class DomainEventRegistrationRemover implements AutoCloseable {
        private final Supplier<?> callOnDispose;

        public DomainEventRegistrationRemover(Supplier<?> toCall) {
            callOnDispose = toCall;
        }

        @Override public void close() {
            callOnDispose.get();
        }
    }

    // Simple test
    public static void main(String[] args) throws Exception {
        AutoCloseable remover = DomainEvents.register((b) -> System.out.printf("Confirmed booking: %s\n", b.altRestaurantBooking.id));

        var booking = new ch17.restaurantBooking.model.DoubleDispatchAlternative.RestaurantBooking();
        booking.id = UUID.randomUUID();

        booking.confirmBooking(); // Prints smth like: Confirmed booking: 0dbc458d-d9a5-48ac-910c-73998ae60919
        remover.close();
        booking.confirmBooking(); // Doesn't print, because an event handler was removed.
    }
}
