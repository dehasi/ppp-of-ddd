package ch.application;

import ch.Bootstrapper;
import ch.application.commands.AddProductToBasketCommand;
import ch.application.readModel.Product;
import ch.application.readModel.ProductRepository;
import ch.infrastructure.DomainEvents;
import ch.model.Money;
import ch.model.baskets.Basket;
import ch.model.baskets.IBasketRepository;
import ch.model.baskets.events.BasketPriceChanged;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ApiTest {

    private static final Bootstrapper.ObjectFactory objectFactory = Bootstrapper.startup();

    private final ProductRepository productRepository = objectFactory.getInstance(ProductRepository.class);
    private final IBasketRepository basketRepository = objectFactory.getInstance(IBasketRepository.class);
    private final Api api = objectFactory.getInstance(Api.class);

    @Test void actionRequestTo_AddProductToBasketCommand_raisesBasketPriceChanged() {
        // Given
        UUID basketId = UUID.randomUUID();
        Basket basket = new Basket(basketId);
        basketRepository.add(basket);
        assertThat(basket.items()).hasSize(0); // The Basket is empty at the beginning

        int productId = 42;
        Product product = new Product(productId, new Money(12));
        productRepository.add(product);

        AddProductToBasketCommand command = new AddProductToBasketCommand(productId, basketId);
        EventsCatcher<BasketPriceChanged> catcher = new EventsCatcher<>();

        // When
        try (var remover = DomainEvents.register(BasketPriceChanged.class, catcher::catchEvent)) {
            api.actionRequestTo(command);
        }

        // Then
        assertThat(catcher.events).hasSize(1);
        assertThat(basket.items()).hasSize(1);
    }

    private static class EventsCatcher<Event> {
        List<Event> events = new ArrayList<>();

        void catchEvent(Event event) {
            events.add(event);
        }
    }
}