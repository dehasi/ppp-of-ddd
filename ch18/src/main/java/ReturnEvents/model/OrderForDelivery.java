package ReturnEvents.model;

import ReturnEvents.model.events.DeliveryGuaranteeFailed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ReturnEvents.model.FoodDeliveryOrderSteps.PENDING;

public class OrderForDelivery {

    public UUID id;
    private LocalDateTime timeOfOrderBeingPlaced;
    private LocalDateTime timeThatPizzaWasDelivered;
    private FoodDeliveryOrderSteps status;
    private DeliveryGuaranteeOffer deliveryGuaranteeOffer;
    public List<Object> RecordedEvents;

    OrderForDelivery(UUID id, UUID customerId, UUID restuarantId, List<Integer> menuItemIds, LocalDateTime timeOfOrderBeingPlaced,
                     DeliveryGuaranteeOffer deliveryGuaranteeOffer) {
        this.id = id;
        this.timeOfOrderBeingPlaced = timeOfOrderBeingPlaced;
        this.status = PENDING;
        this.RecordedEvents = new ArrayList<>();
        this.deliveryGuaranteeOffer = deliveryGuaranteeOffer;
    }

    public void confirmReceipt(LocalDateTime timeThatPizzaWasDelivered) {
        if (status != FoodDeliveryOrderSteps.DELIVERED) {
            this.timeThatPizzaWasDelivered = timeThatPizzaWasDelivered;
            status = FoodDeliveryOrderSteps.DELIVERED;
            if (deliveryGuaranteeOffer.isNotSatisfiedBy(timeOfOrderBeingPlaced, timeThatPizzaWasDelivered)) {
                RecordedEvents.add(new DeliveryGuaranteeFailed(this));
            }
        }
    }
}
