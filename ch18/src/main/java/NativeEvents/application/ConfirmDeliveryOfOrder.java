package NativeEvents.application;

import NativeEvents.application.events.RefundDueToLateDelivery;
import NativeEvents.model.OrderRepository;
import NativeEvents.model.events.DeliveryGuaranteeFailed;

import java.time.LocalDateTime;
import java.util.UUID;

class ConfirmDeliveryOfOrder {
    private OrderRepository orderRepository;
    private IBus bus;

    public ConfirmDeliveryOfOrder(OrderRepository orderRepository, IBus bus) {
        this.orderRepository = orderRepository;
        this.bus = bus;
    }

    public void confirm(LocalDateTime timeThatPizzaWasDelivered, UUID orderId) {
        var order = orderRepository.findBy(orderId);
        order.deliveryGuaranteeFailed.add(this::onDeliveryGuaranteeFailed); // +=
        order.ConfirmReceipt(timeThatPizzaWasDelivered);
    }

    private void onDeliveryGuaranteeFailed(DeliveryGuaranteeFailed evnt) {
        // handle internal event and publish external event to other bounded contexts
        bus.send(new RefundDueToLateDelivery(evnt.order().id));
    }

    private interface IBus {
        void send(RefundDueToLateDelivery refundDueToLateDelivery);
    } // Pretend to be NServiceBus
}
