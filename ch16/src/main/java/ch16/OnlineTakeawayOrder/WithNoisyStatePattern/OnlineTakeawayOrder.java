package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

import ch16.OnlineTakeawayOrder.Address;

import java.util.UUID;

class OnlineTakeawayOrder {

    private final UUID id;
    private final Address address;
    private OnlineTakeawayOrderState state;

    OnlineTakeawayOrder(UUID id, Address address) {
        this.id = id;
        this.address = address;
        this.state = new InKitchenQueue(this);
    }

    void cook() {
        state = state.cook();
    }

    void takeOutOfOven() {
        state = state.takeOutOfOven();
    }

    void toPackage() {
        state = state.toPackage();
    }

    void deliver() {
        state = state.deliver();
    }
}
