package shipping.ShippingArranged;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sales.messages.events.OrderCreated_V2;

@Component
class OrderCreatedHandler {

    @Autowired
    private RabbitTemplate bus;

    @RabbitListener(queues = "sales.orders.OrderCreated.shipping") void handle(OrderCreated_V2 message) {
        System.err.printf(
                "Shipping BC storing: Order: {%s} User: {%s} Shipping Type: {%s} %s\n",
                message.orderId, message.userId, message.shippingTypeId, message.addressId);

        var order = new ShippingOrder();
        order.userId = message.userId;
        order.orderId = message.orderId;
        order.addressId = message.addressId;
        order.shippingTypeId = message.shippingTypeId;

        ShippingDatabase.addOrderDetails(order);
    }
}
