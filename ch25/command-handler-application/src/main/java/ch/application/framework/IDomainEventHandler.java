package ch.application.framework;

import ch.model.baskets.events.IDomainEvent;

public interface IDomainEventHandler<TEvent extends IDomainEvent> {
    void action(TEvent event);
}
