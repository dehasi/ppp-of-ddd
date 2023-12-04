package ch16.OnlineTakeawayOrder.WithExplicitModelling;

import ch16.OnlineTakeawayOrder.Address;

import static java.util.UUID.randomUUID;

class ExampleRun {

    public static void main(String[] args) {
        var takeawayOrder = new InKitchenOnlineTakeawayOrder(randomUUID(), new Address("1", "@", "4"));
        takeawayOrder
                .cook()
                .takeOutOfOven()
                .toPackage()
                .deliver();
    }
}
