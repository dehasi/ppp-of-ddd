package ServiceBus.model.events;

import ServiceBus.model.OrderForDelivery;
import org.springframework.context.ApplicationEvent;

public final class DeliveryGuaranteeFailed extends ApplicationEvent {

    public final OrderForDelivery order;

    public DeliveryGuaranteeFailed(OrderForDelivery order) {
        super(order);

        this.order = order;
    }
}
