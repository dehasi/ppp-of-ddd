package ch12.listing1;

import ch12.common.*;

import java.util.UUID;

class AddProductToBasket {

    private BasketRepository basketRepository;

    private Country country;

    public void add(Product product, UUID basketId) {
        var basket = basketRepository.findBy(basketId);
        var rate = TaxRateService.obtainTaxRateFor(product.id(), country.id());
        var item = new BasketItem(rate, product.id(), product.price());
        basket.add(item);
        // ...
    }
}
