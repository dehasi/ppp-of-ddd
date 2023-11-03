package me.dehasi.ecommerce.explicitLogic.model;

import me.dehasi.replacements.exception.NotImplementedException;

class Quantity {

    public Quantity(int p) {}

    public Quantity add(Quantity quantity) {
        throw new NotImplementedException();
    }

    boolean contains_more_than(Quantity quantity_threshold) {
        throw new NotImplementedException();
    }
}
