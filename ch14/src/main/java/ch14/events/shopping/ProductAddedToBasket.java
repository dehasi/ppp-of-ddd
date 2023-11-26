package ch14.events.shopping;

import java.util.List;
import java.util.UUID;

public record ProductAddedToBasket(UUID basketId, List<UUID> itemsInBasket) {}
