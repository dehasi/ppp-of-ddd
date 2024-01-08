package ch.infrastructure;

import ch.model.baskets.Basket;
import ch.model.baskets.IBasketRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapBasketRepository implements IBasketRepository {
    private final Map<UUID, Basket> database = new HashMap<>();

    @Override public Basket find_by(UUID basketId) {
        return database.get(basketId);
    }

    @Override public void add(Basket basket) {
        if (basket.id == null)
            basket.id = UUID.randomUUID();
        save(basket);
    }

    @Override public void save(Basket basket) {
        database.put(basket.id, basket);
    }
}
