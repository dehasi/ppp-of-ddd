package me.dehasi.ecommerce.implicitLogic.model;

import me.dehasi.replacements.exception.ApplicationException;
import me.dehasi.replacements.exception.NotImplementedException;

// This example, including associated classes, demonstrates the problems of not making concepts explicit.
// It does not demonstrate coding and DDD best practices
class Basket {
    private BasketItems items;

    public void add(Product product) {
        if (basket_contains_an_item_for(product)) {
            var item_quantity = get_item_for(product).quantity()
                    .add(new Quantity(1));
            if (item_quantity.contains_more_than(new Quantity(50)))
                throw new ApplicationException(
                        "You can only purchase 50 of a single product.");
            else
                get_item_for(product).increase_item_quantity_by(new Quantity(1));
        } else
            items.add(BasketItemFactory.create_item_for(product, this));
    }

    private BasketItem get_item_for(Product product) {
        throw new NotImplementedException();
    }

    private boolean basket_contains_an_item_for(Product product) {
        throw new NotImplementedException();
    }
}
