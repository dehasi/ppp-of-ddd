package ch12.listing6_7;

import java.util.UUID;

class Order {
    Order(UUID id, PaymentMethod paymentMethod, Address address) {}

    Order(
            UUID id,
            PaymentMethod paymentMethod,
            Address address,
            PaymentType paymentType) {}

}
