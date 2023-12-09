package StaticDomainEvents.model;

import java.time.Duration;
import java.time.LocalDateTime;

import static zeliba.the.TheComparable.the;

public class ThirtyMinuteDeliveryGuaranteeOffer implements DeliveryGuaranteeOffer {

    @Override public boolean
    isNotSatisfiedBy(LocalDateTime timeOfOrderBeingPlaced, LocalDateTime timeThatPizzaWasDelivered) {
        return the(Duration.between(timeOfOrderBeingPlaced, timeThatPizzaWasDelivered)).isGreaterThan(Duration.ofMinutes(30));
    }
}
