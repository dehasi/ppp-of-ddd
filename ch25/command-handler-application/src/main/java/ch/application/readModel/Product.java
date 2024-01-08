package ch.application.readModel;

import ch.model.Money;
import ch.model.baskets.ProductSnapshot;

public record Product (
    int id,
    Money price){
    public ProductSnapshot snapshot() {
        return new ProductSnapshot(id, price);
    }
}
