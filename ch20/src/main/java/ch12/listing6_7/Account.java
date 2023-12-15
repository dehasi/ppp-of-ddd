package ch12.listing6_7;


import java.util.UUID;

class Account {
    private UUID id;
    private Address address;
    private PaymentMethod paymentMethod;

    Order createOrder() {
        if (hasEnoughCreditToOrder())
            return new Order(this.id, this.paymentMethod, this.address);
        else
            throw new InsufficientCreditToCreateAnOrder();
    }

    Order createAnOrderIgnoringCreditRating() {
        return new Order(this.id, this.paymentMethod, this.address, PaymentType.PAY_BEFORE_SHIPPING);
    }

    private boolean hasEnoughCreditToOrder() {
        return false;
    }

    private static class InsufficientCreditToCreateAnOrder extends RuntimeException {}

}
