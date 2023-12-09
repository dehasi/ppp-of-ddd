package ServiceBus.application;

import ServiceBus.application.events.RefundDueToLateDelivery;
import ServiceBus.model.events.DeliveryGuaranteeFailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class RefundOnDeliveryGuaranteeFailureHandler implements ApplicationListener<DeliveryGuaranteeFailed> {

    @Autowired
    private ApplicationEventPublisher bus;

    @Override public void onApplicationEvent(DeliveryGuaranteeFailed event) {
        // handle internal event and publish external event to other bounded contexts
        bus.publishEvent(new RefundDueToLateDelivery(event.order.id));
    }
}
