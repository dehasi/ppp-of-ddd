package NativeEvents.model;

import NativeEvents.model.events.DeliveryGuaranteeFailed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static NativeEvents.model.FoodDeliveryOrderSteps.DELIVERED;

public class OrderForDelivery {

    public UUID id;
    private LocalDateTime timeOfOrderBeingPlaced;
    private LocalDateTime timeThatPizzaWasDelivered;
    private FoodDeliveryOrderSteps status;
    private DeliveryGuaranteeOffer deliveryGuaranteeOffer;

    public List<DeliveryGuaranteeFailedHandler> deliveryGuaranteeFailed = new ArrayList<>();

    public OrderForDelivery(UUID id, UUID customerId, UUID restuarantId, List<Integer> menuItemIds, LocalDateTime timeOfOrderBeingPlaced) {
        this.id = id;
        this.timeOfOrderBeingPlaced = timeOfOrderBeingPlaced;
        this.status = FoodDeliveryOrderSteps.PENDING;
    }

    public void ConfirmReceipt(LocalDateTime timeThatPizzaWasDelivered) {
        if (status != DELIVERED) {
            this.timeThatPizzaWasDelivered = timeThatPizzaWasDelivered;
            status = DELIVERED;

            if (deliveryGuaranteeOffer.isNotSatisfiedBy(timeOfOrderBeingPlaced, timeThatPizzaWasDelivered)) {
                if (!deliveryGuaranteeFailed.isEmpty())
                    deliveryGuaranteeFailed.forEach(h -> h.handle(new DeliveryGuaranteeFailed(this)));
            }
        }
    }

    @FunctionalInterface
    public interface DeliveryGuaranteeFailedHandler {
        void handle(DeliveryGuaranteeFailed evnt);
    }
}
