package sales.orderCreated;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sales.messages.commands.PlaceOrder;
import sales.messages.events.OrderCreated_V2;

import java.time.LocalDateTime;

@Component
class PlaceOrderHandler {

    @Autowired
    private RabbitTemplate bus;

    @RabbitListener(queues = "sales.orders.PlaceOrder") void handle(PlaceOrder message) {
        var orderId = Database.saveOrder(message.productIds, message.userId, message.shippingTypeId);

        System.err.printf(
                "Created order #{%s} : Products:{%s} with shipping: {%s} made by user: {%s}\n",
                orderId, String.join(",", message.productIds), message.shippingTypeId, message.userId);

        // sending a V2 message now
        var orderCreatedEvent = new OrderCreated_V2();

        orderCreatedEvent.orderId = orderId;
        orderCreatedEvent.userId = message.userId;
        orderCreatedEvent.productIds = message.productIds;
        orderCreatedEvent.shippingTypeId = message.shippingTypeId;
        orderCreatedEvent.timeStamp = LocalDateTime.now();
        orderCreatedEvent.amount = calculateCostOf(message.productIds);
        orderCreatedEvent.addressId = "AddressID123";

        bus.convertAndSend("fanout.OrderCreated", "", orderCreatedEvent);
    }

    private double calculateCostOf(String[] productIds) {
        // database lookup, etc
        return 1000.00;
    }
}
