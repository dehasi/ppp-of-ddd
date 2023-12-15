package ch12.listing2;

import ch12.common.Product;

import java.util.UUID;

class AddProductToBasket {

    private BasketRepository basketRepository;

    void add(Product product, UUID basketId) {
        var basket = basketRepository.findBy(basketId);

        basket.add(product);
    }
}
