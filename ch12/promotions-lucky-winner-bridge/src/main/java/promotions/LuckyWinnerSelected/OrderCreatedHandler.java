package promotions.LuckyWinnerSelected;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sales.messages.events.OrderCreated_V2;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Component
class OrderCreatedHandler {

    @Autowired
    private KafkaTemplate<String, String> queue;

    @RabbitListener(queues = "sales.orders.OrderCreated.promotions") void handle(OrderCreated_V2 message) {
        System.err.printf(
                "Bridge received order: {%s}. " +
                        "About to push it onto Kafka's queue\n",
                message.orderId);

        var kafkaMsg = convertToKafkaJsonMessageFormat(message);
        queue.send("orderCreated", kafkaMsg);
    }

    private String convertToKafkaJsonMessageFormat(OrderCreated_V2 message) {
        return String.format("""
                        {
                        "orderId": "%s",
                        "userId": "%s",
                        "productIds": %s,
                        "shippingTypeId": "%s",
                        "timeStamp": "%s",
                        "amount": %s
                        }
                        """,
                message.orderId,
                message.userId,
                generateProductIdsJson(message.productIds),
                message.shippingTypeId,
                message.timeStamp,
                message.amount);
    }

    private static String generateProductIdsJson(String[] productIds) {
        if (productIds == null) return "null";
        return "[" + stream(productIds).map(id -> '"' + id + '"').collect(joining(",")) + "]";
    }
}
