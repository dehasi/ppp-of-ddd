package ReturnEvents.model;

import ReturnEvents.model.events.DeliveryGuaranteeFailed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//Delivery_guarantee_events_are_recorded_on_guarantee_offer_failure
class OrderForDeliveryTest {

    private boolean eventWasRaised = false;
    private UUID id = UUID.randomUUID();
    private UUID customerId = UUID.randomUUID();
    private UUID restaurantId = UUID.randomUUID();
    private List<Integer> menuItemIds = new ArrayList<>();
    private LocalDateTime timeOrderWasPlaced = LocalDateTime.now().plusMinutes(-30);
    private LocalDateTime timePizzaDelivered = LocalDateTime.now();
    private DeliveryGuaranteeOffer offer = null;
    private OrderForDelivery order = null;

    @BeforeEach void when_confirming_an_order_that_is_late() {
        offer = (timeOrderWasPlaced, timePizzaDelivered) -> true;
        order = new OrderForDelivery(id, customerId, restaurantId, menuItemIds, timeOrderWasPlaced, offer);
        order.confirmReceipt(timePizzaDelivered);
    }

    @Test void a_delivery_guarantee_failed_event_will_be_recorded() {
        var wasRecorded = order.RecordedEvents.stream()
                .filter(o -> o instanceof DeliveryGuaranteeFailed)
                .count() == 1;

        assertThat(wasRecorded).isTrue();
    }
}
