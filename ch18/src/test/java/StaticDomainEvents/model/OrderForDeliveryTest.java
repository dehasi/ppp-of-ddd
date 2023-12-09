package StaticDomainEvents.model;

import StaticDomainEvents.infrastructure.DomainEvents;
import StaticDomainEvents.model.events.DeliveryGuaranteeFailed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//  Delivery_guarantee_events_are_raised_on_guarantee_offer_failure
class OrderForDeliveryTest {

    private boolean eventWasRaised = false;
    private UUID id = UUID.randomUUID();
    private UUID customerId = UUID.randomUUID();
    private UUID restaurantId = UUID.randomUUID();
    private List<Integer> menuItemIds = new ArrayList<>();
    private LocalDateTime timeOrderWasPlaced = LocalDateTime.now().plusMinutes(-30);
    private LocalDateTime timePizzaDelivered = LocalDateTime.now();
    private DeliveryGuaranteeOffer offer = null; // MockRepository.GenerateStub<IDeliveryGuaranteeOffer>();

    @BeforeEach void when_confirming_an_order_that_is_late() {
        offer = (timeOrderWasPlaced, timePizzaDelivered) -> true;
        var order = new OrderForDelivery(id, customerId, restaurantId, menuItemIds, timeOrderWasPlaced, offer);

        try (var remover = DomainEvents.register(DeliveryGuaranteeFailed.class, this::setTestFlag)) {
            order.confirmReceipt(timePizzaDelivered);
        }
    }

    private void setTestFlag(DeliveryGuaranteeFailed obj) {
        eventWasRaised = true;
    }


    @Test void a_delivery_guarantee_failed_event_will_be_raised() {
        assertThat(eventWasRaised).isTrue();
    }
}