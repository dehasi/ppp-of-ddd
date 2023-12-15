package ch12.listing8.domain;

public class DeliveryFactory {

    public DeliveryOption create(int deliveryOptionId) {return new DeliveryOption();}

    public static DeliveryOption createNonChosen() {return null;}
}
