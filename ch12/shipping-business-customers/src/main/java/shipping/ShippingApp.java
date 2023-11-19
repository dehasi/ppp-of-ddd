package shipping;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShippingApp {
    public static void main(String[] args) {
        SpringApplication.run(ShippingApp.class, args);
    }

    @Bean Queue aaaorderCreatedQueue() {
        return new Queue("shipping.events.ShippingArranged", false);
    }

    @Bean Queue paymentAcceptedQueue() {
        return new Queue("billing.payments.PaymentAccepted", false);
    }
}
