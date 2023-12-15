package ch12.listing8.infrastructure;

import ch12.listing8.domain.Basket;
import ch12.listing8.domain.BasketDTO;
import ch12.listing8.domain.BasketFactory;

import java.util.UUID;

class BasketRepository implements IBasketRepository {
    @Override public Basket FindBy(UUID id) {
        BasketDTO rawData = ExternalService.obtainBasket(id.toString());
        var basket = BasketFactory.reconstituteBasketFrom(rawData);
        return basket;
    }
}
