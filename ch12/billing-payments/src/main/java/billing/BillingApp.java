package billing;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
class BillingApp {
    public static void main(String[] args) {
        SpringApplication.run(BillingApp.class, args);
    }

    @Bean Queue paymentAcceptedQueue() {
        return new Queue("billing.payments.PaymentAccepted", false);
    }

    @Bean Queue local() {
        return new Queue("local", false);
    }
}
