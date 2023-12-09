package StaticDomainEvents.application;

import StaticDomainEvents.application.ConfirmDeliveryOfOrder.IBus;
import StaticDomainEvents.application.events.RefundDueToLateDelivery;
import StaticDomainEvents.model.OrderForDelivery;
import StaticDomainEvents.model.OrderRepository;
import StaticDomainEvents.model.ThirtyMinuteDeliveryGuaranteeOffer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Delivery_guarantee_failed
class ConfirmDeliveryOfOrderTest {

    IBus bus = new BusStub();
    // when testing at service layer may want to use concrete repository
    OrderRepository repo;

    UUID orderId = UUID.randomUUID();
    UUID customerId = UUID.randomUUID();
    UUID restaurantId = UUID.randomUUID();
    List<Integer> itemIds = List.of(123, 456, 789);
    LocalDateTime timeOrderWasPlaced = LocalDateTime.of(2015, 03, 01, 20, 15, 0);


    @BeforeEach void if_an_order_is_not_delivered_within_the_agreed_upon_timeframe() {
        var offer = new ThirtyMinuteDeliveryGuaranteeOffer();

        // took longer than 30 minutes - failing the delivery guarantee
        var timeOrderWasReceived = timeOrderWasPlaced.plusMinutes(31);

        var order = new OrderForDelivery(
                orderId, customerId, restaurantId, itemIds, timeOrderWasPlaced, offer
        );

        repo = id -> order;
        var service = new ConfirmDeliveryOfOrder(repo, bus);
        service.confirm(timeOrderWasReceived, orderId);
    }

    @Test void an_external_refund_due_to_late_delivery_instruction_will_be_published() {
        // get the first message published during execution of this use case
        var message = ((BusStub) bus).argumentsForCallsMadeOnSend.get(0);
        Assertions.assertThat(message).isNotNull();
        var refund = (RefundDueToLateDelivery) message;

        Assertions.assertThat(refund.orderId()).isEqualTo(orderId);
    }

    private static class BusStub implements IBus {
        List<Object> argumentsForCallsMadeOnSend = new ArrayList<>();

        @Override public void send(RefundDueToLateDelivery event) {
            argumentsForCallsMadeOnSend.add(event);
        }
    }
}