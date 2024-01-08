package ch.model.baskets;

import java.util.UUID;

public interface IBasketRepository {

    Basket find_by(UUID basketId);
    void add(Basket basket);
    void save(Basket basket);
}
