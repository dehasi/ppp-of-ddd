package ch14.events.shopping;

import ch14.entity.Product;

import java.util.UUID;

class Basket {

    private UUID id;
    private BasketItems items;

    public void add(Product product) {
        items.add(BasketItemFactory.createItemFor(product, this));

        DomainEvents.raise(new ProductAddedToBasket(id, items.idsOfItemsInBasket()));
    }
}
