package ch16.customer.DistributionAwareCustomer.BillingBoundedContext;

import ch16.customer.CardDetails;

import java.util.UUID;

record PaymentDetails(UUID customerId, CardDetails Default, CardDetails alternate) {}
