package ch16.customer;

public record PaymentDetails(CardDetails defaultPayment, CardDetails alternatePayment) {}
