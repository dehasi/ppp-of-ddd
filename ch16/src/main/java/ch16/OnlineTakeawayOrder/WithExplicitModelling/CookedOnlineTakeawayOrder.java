package ch16.OnlineTakeawayOrder.WithExplicitModelling;

import ch16.OnlineTakeawayOrder.Address;

import java.util.UUID;

class CookedOnlineTakeawayOrder {
    private final UUID id;
    private final Address address;

    CookedOnlineTakeawayOrder(UUID id, Address address) {
        this.id = id;
        this.address = address;
    }

    public OutForDeliveryOnlineTakeawayOrder toPackage() {
        // ..
        return new OutForDeliveryOnlineTakeawayOrder(this.id, this.address);
    }
}
