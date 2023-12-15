package ch12.listing3;

import ch12.common.BasketItem;
import ch12.common.Country;
import ch12.common.Product;

import java.util.List;

class Basket {

    private Country deliveryAddress;

    private List<BasketItem> items;

    void add(Product product) {
        if (contains(product)) {
            getItemFor(product).increaseItemQuantityBy(1);
        } else {
            items.add(BasketItemFactory.createItemFor(product, deliveryAddress));
        }
    }

    private BasketItem getItemFor(Product product) {
        return null;
    }

    private boolean contains(Product product) {
        return false;
    }
}
