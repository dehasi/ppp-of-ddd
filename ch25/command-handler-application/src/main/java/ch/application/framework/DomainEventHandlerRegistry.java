package ch.application.framework;

import ch.model.baskets.events.IDomainEvent;

import java.util.HashMap;
import java.util.Map;

public class DomainEventHandlerRegistry implements IDomainEventHandlerRegistry {
    Map<Class<?>, IDomainEventHandler<?>> handlers = new HashMap<>();

    public <TEvent extends IDomainEvent> void handle(TEvent domain_event) {
        handlers.get(domain_event.getClass());
        // not sure if it can work in Java
        IDomainEventHandler<TEvent> handler = (IDomainEventHandler<TEvent>) handlers.get(domain_event.getClass());

        handler.action(domain_event);
    }
}
