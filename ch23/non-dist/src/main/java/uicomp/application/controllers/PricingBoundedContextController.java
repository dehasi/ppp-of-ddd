package uicomp.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Controller
class PricingBoundedContextController {

    @Autowired SpringTemplateEngine templateEngine;

    public String price(String productId) {
        /* convention will look for a partial view called:
         * templates/PricingBoundedContext/price.html
         */
        Context myContext = new Context();

        var price = PricingBoundedContext.PriceFinder.priceFor(productId);

        myContext.setVariable("price", price);

        return templateEngine.process("PricingBoundedContext/price", myContext);
    }

    static class PricingBoundedContext {
        static class PriceFinder {
            private static final Random RANDOM = new Random();

            static int priceFor(String productId) {
                return RANDOM.nextInt(1, 1000);
            }
        }
    }
}
