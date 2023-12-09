package StaticDomainEvents.application.events;

import java.util.UUID;

public record RefundDueToLateDelivery(UUID orderId) {}
