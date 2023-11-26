package ch14.events.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class BasketItems {

    private final List<BasketItem> basketItems = new ArrayList<>();

    public List<UUID> idsOfItemsInBasket() {
        return basketItems.stream().map(BasketItem::id).toList();
    }

    public void add(BasketItem item) {
        basketItems.add(item);
    }
}
