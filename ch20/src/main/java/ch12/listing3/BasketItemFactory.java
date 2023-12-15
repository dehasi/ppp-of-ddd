package ch12.listing3;

import ch12.common.BasketItem;
import ch12.common.Country;
import ch12.common.Product;
import ch12.common.TaxRateService;

class BasketItemFactory {

    static BasketItem createItemFor(Product product, Country country) {
        var rate = TaxRateService.obtainTaxRateFor(product.id(), country.id());

        return new BasketItem(rate, product.id(), product.price());
    }
}
