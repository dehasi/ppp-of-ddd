package ch16.customer.DistributionAwareCustomer.AccountsBoundedContext;

import ch16.customer.Orders;

import java.util.UUID;

record CustomerOrderHistory(UUID customerId, Orders orders) {}
