package ch15.examples;

import java.util.UUID;

// Entity
record BankAccount(UUID id, Money balance) {}
