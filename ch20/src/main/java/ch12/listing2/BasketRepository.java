package ch12.listing2;


import java.util.UUID;

interface BasketRepository {
    Basket findBy(UUID basketId);
}
