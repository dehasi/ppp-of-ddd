package ch16.OnlineTakeawayOrder.WithExplicitModelling;

import ch16.OnlineTakeawayOrder.Address;

import java.util.UUID;

class InOvenOnlineTakeawayOrder {

    final UUID id;
    final Address address;

    public InOvenOnlineTakeawayOrder(UUID id, Address address) {
        this.id = id;
        this.address = address;
    }

    public CookedOnlineTakeawayOrder takeOutOfOven()
    {
        // ..
        return new CookedOnlineTakeawayOrder(this.id, this.address);
    }
}
