package ch16.customer.DistributionAwareCustomer.MarketingBoundedContext;

import ch16.customer.LoyaltySummary;

import java.util.UUID;

record Loyalty(UUID customerId, LoyaltySummary loyalty) {}
