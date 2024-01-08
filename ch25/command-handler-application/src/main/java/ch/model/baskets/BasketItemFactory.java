package ch.model.baskets;

class BasketItemFactory {
    public static BasketItem create_item_for(ProductSnapshot productSnapshot) {
        return new BasketItem(productSnapshot, new NonNegativeQuantity(1));
    }
}
