package me.dehasi.ecommerce.explicitLogic.model;

import me.dehasi.replacements.exception.NotImplementedException;

//  This example, including associated classes, accentuates making logic explicit.
//  It does not demonstrate coding and DDD best practices
class basket {
    private BasketItems items;
    private OverSeasSellingPolicy overSeasSellingPolicy;

    public void add(Product product) {
        if (basket_contains_an_item_for(product)) {
            var item_quantity = get_item_for(product).quantity()
                    .add(new Quantity(1));
            if (overSeasSellingPolicy.is_satisfied_by(item_quantity))
                get_item_for(product).increase_item_quantity_by(new Quantity(1));
            else
                throw new OverSeasSellingPolicyException(
                        String.format(
                                "You can only purchase {%s} of a single product.",
                                OverSeasSellingPolicy.quantity_threshold)
                );
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
