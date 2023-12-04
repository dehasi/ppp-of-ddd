package ch16.OnlineTakeawayOrder.WithExplicitModelling;

import ch16.OnlineTakeawayOrder.Address;

import java.util.UUID;

class OutForDeliveryOnlineTakeawayOrder {

    final UUID id;
    final Address address;

    OutForDeliveryOnlineTakeawayOrder(UUID id, Address address) {
        this.id = id;
        this.address = address;
    }

    public void deliver() {
        // ..
    }
}
