package billing.PaymentAccepted;

import billing.messages.commands.PaymentStatus;
import billing.messages.commands.RecordPaymentAttempt;
import billing.messages.events.PaymentAccepted;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RecordPaymentAttemptHandler {

    @Autowired
    private RabbitTemplate bus;

    @RabbitListener(queues = "local") void handle(RecordPaymentAttempt message) {
        Database.savePaymentAttempt(message.orderId, message.status);

        if (message.status == PaymentStatus.Accepted) {
            var evnt = new PaymentAccepted();
            evnt.orderId = message.orderId;


            bus.convertAndSend("billing.payments.PaymentAccepted", evnt);
            System.err.printf(
                    "Received payment accepted notification for Order: {%s}. Published PaymentAccepted event\n",
                    message.orderId);
        } else {
            // publish a payment rejected event
        }
    }
}
