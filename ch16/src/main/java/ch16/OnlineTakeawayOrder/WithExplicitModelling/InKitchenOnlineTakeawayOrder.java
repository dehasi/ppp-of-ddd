package ch16.OnlineTakeawayOrder.WithExplicitModelling;

import ch16.OnlineTakeawayOrder.Address;

import java.util.UUID;

class InKitchenOnlineTakeawayOrder {

    final UUID id;
    final Address address;

    InKitchenOnlineTakeawayOrder(UUID id, Address address) {
        this.id = id;
        this.address = address;
    }

    // Only contains methods it actually implements
    // returns new state so that clients have to be aware of it
    InOvenOnlineTakeawayOrder cook() {
        // ..
        return new InOvenOnlineTakeawayOrder(this.id, this.address);
    }
}
