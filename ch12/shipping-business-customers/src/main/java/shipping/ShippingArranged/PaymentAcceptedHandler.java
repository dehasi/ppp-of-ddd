package shipping.ShippingArranged;

import billing.messages.events.PaymentAccepted;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shipping.events.ShippingArranged;

import static shipping.ShippingArranged.ShippingStatus.Success;

@Component
class PaymentAcceptedHandler {

    @Autowired
    private RabbitTemplate bus;

    @RabbitListener(queues = "billing.payments.PaymentAccepted") void handle(PaymentAccepted message) {
        var address = ShippingDatabase.getCustomerAddress(message.orderId);
        var confirmation = ShippingProvider.arrangeShippingFor(address, message.orderId);

        if (confirmation.status == Success) {
            var evnt = new ShippingArranged();
            evnt.orderId = message.orderId;

            bus.convertAndSend("shipping.events.ShippingArranged", evnt);
            System.err.printf(
                    "Shipping BC arranged shipping for Order: {%s} %s\n",
                    message.orderId, address);
        } else {
            // .. notify failed shipping instead
        }
    }
}
