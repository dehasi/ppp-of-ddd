package ch12.listing8.infrastructure;

import ch12.listing8.domain.Basket;

import java.util.UUID;

public interface IBasketRepository {
    Basket FindBy(UUID id);
}
