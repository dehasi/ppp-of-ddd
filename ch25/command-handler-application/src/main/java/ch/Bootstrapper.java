package ch;

import ch.application.Api;
import ch.application.commands.AddProductToBasketCommand;
import ch.application.framework.CommandHandlerRegistry;
import ch.application.framework.ICommandHandlerRegistry;
import ch.application.handlers.AddProductToBasketCommandHandler;
import ch.application.readModel.HashMapProductRepository;
import ch.application.readModel.ProductRepository;
import ch.infrastructure.HashMapBasketRepository;
import ch.infrastructure.HashMapPromotionsRepository;
import ch.model.PromotionsNamespace.IPromotionsRepository;
import ch.model.baskets.BasketPricingService;
import ch.model.baskets.IBasketPricingService;
import ch.model.baskets.IBasketRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup() {
        IBasketRepository basketRepository = new HashMapBasketRepository();
        ProductRepository productRepository = new HashMapProductRepository();
        IPromotionsRepository promotionsRepository = new HashMapPromotionsRepository();

        IBasketPricingService basketPricingService = new BasketPricingService(basketRepository, promotionsRepository);
        AddProductToBasketCommandHandler addProductToBasketCommandHandler = new AddProductToBasketCommandHandler(basketRepository, productRepository, basketPricingService);

        ICommandHandlerRegistry commandHandlerRegistry = new CommandHandlerRegistry();
        commandHandlerRegistry.register_handler_for(AddProductToBasketCommand.class, addProductToBasketCommandHandler);

        Api api = new Api(commandHandlerRegistry);

        return new ObjectFactory(toBeans(List.of(
                basketRepository,
                productRepository,
                promotionsRepository,
                basketPricingService,
                api
        )));
    }

    private static Map<Class<?>, Object> toBeans(List<Object> classes) {
        Map<Class<?>, Object> beans = new HashMap<>();
        classes.forEach(instance -> beans.put(instance.getClass(), instance));
        return beans;
    }

    @SuppressWarnings("unchecked")
    public static class ObjectFactory {
        private final Map<Class<?>, Object> beans;

        private ObjectFactory(Map<Class<?>, Object> beans) {this.beans = beans;}

        public <T> T getInstance(Class<T> clazz) {
            Class<?> aClass = beans.keySet().stream()
                    .filter(clazz::isAssignableFrom)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Can't find bean for + " + clazz));

            return (T) beans.get(aClass);
        }
    }
}
