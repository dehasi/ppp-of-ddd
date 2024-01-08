package ch.model.baskets.events;

import ch.model.Money;

import java.util.UUID;

public record BasketPriceChanged(
        UUID basket_id,
        Money cost_of_basket,
        Money discount
) implements IDomainEvent {
}
