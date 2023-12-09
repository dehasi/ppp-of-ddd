package StaticDomainEvents.model.events;

import StaticDomainEvents.model.OrderForDelivery;

public record DeliveryGuaranteeFailed(OrderForDelivery order) {}
