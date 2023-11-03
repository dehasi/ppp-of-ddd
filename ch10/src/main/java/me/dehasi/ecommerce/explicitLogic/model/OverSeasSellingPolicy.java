package me.dehasi.ecommerce.explicitLogic.model;


import me.dehasi.replacements.exception.NotImplementedException;

class OverSeasSellingPolicy {
    public static final Quantity quantity_threshold = new Quantity(50);

    public boolean is_satisfied_by(Quantity item_quantity, Country country)    {
        if (item_quantity.contains_more_than(quantity_threshold))
            return false;
        else
            return true;
    }


    boolean is_satisfied_by(Quantity item_quantity) {
        throw new NotImplementedException();
    }
}
