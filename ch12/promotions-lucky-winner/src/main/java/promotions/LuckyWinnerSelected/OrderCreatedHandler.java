package promotions.LuckyWinnerSelected;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class OrderCreatedHandler {

    @KafkaListener(topics = "orderCreated", groupId = "promotions", containerFactory = "orderCreatedKafkaListenerContainerFactory")
    public void handle(OrderCreated message) {
        System.out.printf(
                "Kafka handling order placed event: Order:" +
                        " {%s} for User: {%s}\n",
                message.getOrderId(), message.getUserId());
    }
}
