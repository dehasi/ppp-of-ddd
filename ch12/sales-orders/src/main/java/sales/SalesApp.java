package sales;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalesApp {
    public static void main(String[] args) {
        SpringApplication.run(SalesApp.class, args);
    }

    @Bean
    public Declarables orderCreatedBindings() {
        Queue fanoutQueue1 = new Queue("sales.orders.OrderCreated.shipping", false);
        Queue fanoutQueue2 = new Queue("sales.orders.OrderCreated.billing", false);
        Queue fanoutQueue3 = new Queue("sales.orders.OrderCreated.promotions", false);
        FanoutExchange fanoutExchange = new FanoutExchange("fanout.OrderCreated");

        return new Declarables(
                fanoutQueue1,
                fanoutQueue2,
                fanoutQueue3,
                fanoutExchange,
                BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
                BindingBuilder.bind(fanoutQueue2).to(fanoutExchange),
                BindingBuilder.bind(fanoutQueue3).to(fanoutExchange));
    }

    @Bean Queue placeOrderQueue() {
        return new Queue("sales.orders.PlaceOrder", false);
    }
}
