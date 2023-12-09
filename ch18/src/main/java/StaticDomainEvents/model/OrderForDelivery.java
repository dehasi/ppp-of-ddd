package StaticDomainEvents.model;

import StaticDomainEvents.infrastructure.DomainEvents;
import StaticDomainEvents.model.events.DeliveryGuaranteeFailed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static StaticDomainEvents.model.FoodDeliveryOrderSteps.DELIVERED;
import static StaticDomainEvents.model.FoodDeliveryOrderSteps.PENDING;

public class OrderForDelivery {

    public UUID id;
    private LocalDateTime timeOfOrderBeingPlaced;
    private LocalDateTime timeThatPizzaWasDelivered;
    private FoodDeliveryOrderSteps status;
    private DeliveryGuaranteeOffer deliveryGuaranteeOffer;

    public OrderForDelivery(UUID id, UUID customerId, UUID restuarantId, List<Integer> menuItemIds, LocalDateTime timeOfOrderBeingPlaced,
                            DeliveryGuaranteeOffer deliveryGuaranteeOffer) {
        this.id = id;
        this.timeOfOrderBeingPlaced = timeOfOrderBeingPlaced;
        this.status = PENDING;
        this.deliveryGuaranteeOffer = deliveryGuaranteeOffer;
    }

    public void confirmReceipt(LocalDateTime timeThatPizzaWasDelivered) {
        if (status != DELIVERED) {
            this.timeThatPizzaWasDelivered = timeThatPizzaWasDelivered;
            status = FoodDeliveryOrderSteps.DELIVERED;

            if (deliveryGuaranteeOffer.isNotSatisfiedBy(timeOfOrderBeingPlaced, timeThatPizzaWasDelivered)) {
                DomainEvents.raise(new DeliveryGuaranteeFailed(this));
            }
        }
    }
}
