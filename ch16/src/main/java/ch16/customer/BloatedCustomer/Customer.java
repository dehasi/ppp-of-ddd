package ch16.customer.BloatedCustomer;

import ch16.customer.AddressBook;
import ch16.customer.LoyaltySummary;
import ch16.customer.Orders;
import ch16.customer.PaymentDetails;

import java.util.UUID;

class Customer {

    final UUID id;
    final AddressBook addresses;
    final Orders orderHistory;
    final PaymentDetails paymentDetails;
    final LoyaltySummary loyalty;

    Customer(UUID id, AddressBook addresses, Orders orderHistory, PaymentDetails paymentDetails, LoyaltySummary loyalty) {
        this.id = id;
        this.addresses = addresses;
        this.orderHistory = orderHistory;
        this.paymentDetails = paymentDetails;
        this.loyalty = loyalty;
    }
}
