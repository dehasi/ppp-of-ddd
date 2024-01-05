package uicomp.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Controller
class ShippingBoundedContextController {

    @Autowired SpringTemplateEngine templateEngine;

    public String deliveryOptions() {
        Context myContext = new Context();

        var options = ShippingBoundedContext.DeliveryOptions.all();

        myContext.setVariable("options", options);

        return templateEngine.process("ShippingBoundedContext/delivery-options", myContext);
    }

    static class ShippingBoundedContext {
        static class DeliveryOptions {
            static List<DeliveryOption> all() {
                return List.of(
                        new DeliveryOption(
                                "ss1",
                                "Cheap & Cheerful",
                                2,
                                new Tuple(7, 14)
                        ),
                        new DeliveryOption(
                                "ss2",
                                "Super Fast",
                                50,
                                new Tuple(1, 2)
                        )
                );
            }
        }

        record DeliveryOption(
                String id,
                String name,
                int price,
                Tuple duration
        ) {}

        record Tuple(int min, int max) {}
    }
}
