package ch16.customer;

import java.util.UUID;

public record CardDetails(UUID id, String number, String securityCode) {}
