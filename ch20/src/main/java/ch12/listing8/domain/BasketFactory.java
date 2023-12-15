package ch12.listing8.domain;

public class BasketFactory {
    public static Basket reconstituteBasketFrom(BasketDTO rawData) {
        Basket basket = new Basket();
        // ...
        var deliveryOption = new DeliveryFactory().create(rawData.deliveryOptionId());
        if (deliveryOption.canBeUsedFor(rawData.items()))
            basket.set(deliveryOption);
        else
            basket.set(DeliveryFactory.createNonChosen());
        // .....
        return basket;
    }
}
