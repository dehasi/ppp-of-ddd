package ch.model.baskets.events;

import ch.model.Money;

import java.util.UUID;

public record BasketCreated(UUID id, Money amountToPay) implements IDomainEvent {}
