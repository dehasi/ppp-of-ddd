package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

import ch16.OnlineTakeawayOrder.Address;

import static java.util.UUID.randomUUID;

class ExampleRun {
    public static void main(String[] args) {
        var order = new OnlineTakeawayOrder(randomUUID(), new Address("31", "$", "#"));
        order.cook();
        order.takeOutOfOven();
        order.toPackage();
        order.deliver();
    }
}
