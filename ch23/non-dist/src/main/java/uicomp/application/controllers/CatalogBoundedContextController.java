package uicomp.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static uicomp.application.controllers.CatalogBoundedContextController.SalesBoundedContext.ProductFinder;

@Controller
class CatalogBoundedContextController {

    @Autowired private SpringTemplateEngine templateEngine;

    public String itemInBasket(String productId) {
        Context myContext = new Context();

        var product = ProductFinder.find(productId);

        myContext.setVariable("product", product);

        return templateEngine.process("CatalogBoundedContext/item-in-basket", myContext);
    }

    static class SalesBoundedContext {

        static class ProductFinder {
            static Product find(String productId) {
                return new Product(
                        productId,
                        "Product_" + productId,
                        "book-cover.png",
                        "Lorem ipsum dolor sit amet");
            }
        }

        record Product(
                String id,
                String name,
                String imageUrl,
                String description) {}
    }
}
