package ch12.listing5;

import ch12.common.Product;

class Basket {

    WithListItem moveToWishList(Product product) {
        if (basketContainsAnItemFor(product)) {
            var withListItem = WithListItemFactory.createFrom(getItemFor(product));

            removeItemFor(product);

            return withListItem;
        }
        return null;
    }

    private void removeItemFor(Product product) {}

    private Item getItemFor(Product product) {
        return null;
    }

    private boolean basketContainsAnItemFor(Product product) {
        return false;
    }

}
