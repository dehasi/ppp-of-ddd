package ReturnEvents.application;

import ReturnEvents.infrastructure.EventDispatcher;
import ReturnEvents.model.OrderRepository;

import java.time.LocalDateTime;
import java.util.UUID;

class ConfirmDeliveryOfOrder {
    private OrderRepository orderRepository;
    private EventDispatcher dispatcher;

    public ConfirmDeliveryOfOrder(OrderRepository orderRepository, EventDispatcher dispatcher) {
        this.orderRepository = orderRepository;
        this.dispatcher = dispatcher;
    }

    public void confirm(LocalDateTime timeThatPizzaWasDelivered, UUID orderId) {
        var order = orderRepository.findBy(orderId);
        order.confirmReceipt(timeThatPizzaWasDelivered);

        for (var evnt : order.RecordedEvents) {
            dispatcher.dispatch(evnt);
        }
    }
}
