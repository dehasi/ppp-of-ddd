package StaticDomainEvents.application;

import StaticDomainEvents.application.events.RefundDueToLateDelivery;
import StaticDomainEvents.infrastructure.DomainEvents;
import StaticDomainEvents.model.OrderRepository;
import StaticDomainEvents.model.events.DeliveryGuaranteeFailed;

import java.time.LocalDateTime;
import java.util.UUID;

class ConfirmDeliveryOfOrder {

    private final OrderRepository orderRepository;
    private final IBus bus;

    public ConfirmDeliveryOfOrder(OrderRepository orderRepository, IBus bus) {
        this.orderRepository = orderRepository;
        this.bus = bus;
    }

    public void confirm(LocalDateTime timeThatPizzaWasDelivered, UUID orderId) {
        try (var remover = DomainEvents.register(DeliveryGuaranteeFailed.class, this::onDeliveryFailure)) {
            var order = orderRepository.findBy(orderId);
            order.confirmReceipt(timeThatPizzaWasDelivered);
        }
    }

    private void onDeliveryFailure(DeliveryGuaranteeFailed evnt) {
        // handle internal event and publish external event to other bounded contexts
        bus.send(new RefundDueToLateDelivery(evnt.order().id));
    }

    interface IBus {
        void send(RefundDueToLateDelivery refundDueToLateDelivery);
    }
}
