package ServiceBus.application;

import ServiceBus.model.OrderRepository;

import java.time.LocalDateTime;
import java.util.UUID;

class ConfirmDeliveryOfOrder {
    private final OrderRepository orderRepository;

    public ConfirmDeliveryOfOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void confirm(LocalDateTime timeThatPizzaWasDelivered, UUID orderId) {
        var order = orderRepository.findBy(orderId);
        order.confirmReceipt(timeThatPizzaWasDelivered);
    }
}
