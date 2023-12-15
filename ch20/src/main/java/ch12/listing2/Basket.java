package ch12.listing2;

import ch12.common.BasketItem;
import ch12.common.Country;
import ch12.common.Product;
import ch12.common.TaxRateService;

import java.util.List;

class Basket {

    private Country country;

    private List<BasketItem> items;

    void add(Product product) {
        if (contains(product)) {
            getItemFor(product).increaseItemQuantityBy(1);
        } else {
            var rate = TaxRateService.obtainTaxRateFor(product.id(), country.id());


            var item = new BasketItem(rate, product.id(), product.price());
            items.add(item);
        }
    }

    private BasketItem getItemFor(Product product) {
        return null;
    }

    private boolean contains(Product product) {
        return false;
    }
}
