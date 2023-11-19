package billing.PaymentAccepted;

import billing.messages.commands.RecordPaymentAttempt;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sales.messages.events.OrderCreated;

@Component
class OrderCreatedHandler {

    @Autowired
    private RabbitTemplate bus;

    @RabbitListener(queues = "sales.orders.OrderCreated.billing") void handle(OrderCreated message) {
        System.err.printf("Received order created event: OrderId: %s\n", message.orderId);


        var cardDetails = Database.getCardDetailsFor(message.userId);
        // It should not send ACK if an Exception thrown.
        var confirmation = PaymentProvider.chargeCreditCard(cardDetails, message.amount);

        var command = new RecordPaymentAttempt();
        command.orderId = message.orderId;
        command.status = confirmation.status;

        bus.convertAndSend("local", command); // SendLocal
    }
}
