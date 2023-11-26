package ch14.events.shopping;

import ch14.entity.Product;

import java.util.UUID;

public class BasketItemFactory {

    public static BasketItem createItemFor(Product product, Basket basket) {
        return new BasketItem(UUID.randomUUID());
    }
}
