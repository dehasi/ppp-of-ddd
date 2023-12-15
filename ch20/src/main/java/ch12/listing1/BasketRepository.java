package ch12.listing1;

import ch12.common.Basket;

import java.util.UUID;

interface BasketRepository {
    Basket findBy(UUID basketId);
}
