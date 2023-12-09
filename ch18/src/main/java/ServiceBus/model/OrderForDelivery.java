package ServiceBus.model;

import ServiceBus.model.events.DeliveryGuaranteeFailed;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ServiceBus.model.FoodDeliveryOrderSteps.PENDING;

public class OrderForDelivery {

    public UUID id;
    private LocalDateTime timeOfOrderBeingPlaced;
    private LocalDateTime timeThatPizzaWasDelivered;
    private FoodDeliveryOrderSteps status;
    private DeliveryGuaranteeOffer deliveryGuaranteeOffer;
    private ApplicationEventPublisher bus;


    public OrderForDelivery(UUID id, UUID customerId, UUID restuarantId, List<Integer> menuItemIds, LocalDateTime timeOfOrderBeingPlaced, ApplicationEventPublisher bus) {
        this.id = id;
        this.timeOfOrderBeingPlaced = timeOfOrderBeingPlaced;
        this.status = PENDING;
        this.bus = bus;
    }

    public void confirmReceipt(LocalDateTime timeThatPizzaWasDelivered) {

        if (status != FoodDeliveryOrderSteps.DELIVERED) {
            this.timeThatPizzaWasDelivered = timeThatPizzaWasDelivered;
            status = FoodDeliveryOrderSteps.DELIVERED;

            if (deliveryGuaranteeOffer.isNotSatisfiedBy(timeOfOrderBeingPlaced, timeThatPizzaWasDelivered)) {
                bus.publishEvent(new DeliveryGuaranteeFailed(this));
            }
        }
    }
}
