package ServiceBus.application.events;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public final class RefundDueToLateDelivery extends ApplicationEvent {
    public RefundDueToLateDelivery(UUID id) {super(id);}
}
