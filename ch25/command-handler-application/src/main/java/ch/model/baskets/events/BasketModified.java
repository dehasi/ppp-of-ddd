package ch.model.baskets.events;

import java.util.UUID;

public record BasketModified(UUID basketId) implements IDomainEvent {}
