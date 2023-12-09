package ServiceBus.model;

import java.time.LocalDateTime;

public interface DeliveryGuaranteeOffer {

    boolean isNotSatisfiedBy(LocalDateTime timeOfOrderBeingPlaced, LocalDateTime timeThatPizzaWasDelivered);
}
