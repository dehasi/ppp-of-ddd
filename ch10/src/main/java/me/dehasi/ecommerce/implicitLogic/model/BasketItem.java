package me.dehasi.ecommerce.implicitLogic.model;

import me.dehasi.replacements.exception.NotImplementedException;

class BasketItem {

    public Quantity quantity() {
        throw new NotImplementedException();
    }

    void increase_item_quantity_by(Quantity quantity) {
        throw new NotImplementedException();
    }
}
