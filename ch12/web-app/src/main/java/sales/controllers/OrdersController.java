package sales.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sales.messages.commands.PlaceOrder;

import java.time.LocalDateTime;

@Controller
class OrdersController {

    @Autowired
    private RabbitTemplate bus;

    @GetMapping("/orders") String index() {
        return view();
    }

    @PostMapping("/orders/place") String place(@RequestParam("userId") String userId, @RequestParam("productIds") String productIds, @RequestParam("shippingTypeId") String shippingTypeId) {
        var realProductIds = productIds.split(",");
        var placeOrderCommand = new PlaceOrder();
        placeOrderCommand.userId = userId;
        placeOrderCommand.productIds = realProductIds;
        placeOrderCommand.shippingTypeId = shippingTypeId;
        placeOrderCommand.timeStamp = LocalDateTime.now();

        bus.convertAndSend("sales.orders.PlaceOrder", placeOrderCommand);
        // MvcApplication.Bus.Send("sales.orders.OrderCreated", placeOrderCommand);
        // ^ here we send a command to a particular command handler AFAIU
        return content("Your order has been placed. You will receive email confirmation shortly.");
    }

    private static String view() {
        return "index";
    }

    private static String content(String content) {
        return "created";
    }
}
