package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

class InKitchenQueue implements OnlineTakeawayOrderState {

    private final OnlineTakeawayOrder order;

    InKitchenQueue(OnlineTakeawayOrder order) {
        this.order = order;
    }

    @Override public OnlineTakeawayOrderState cook() {
        return new InOven(order);
    }

    @Override public OnlineTakeawayOrderState takeOutOfOven() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState toPackage() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState deliver() {
        throw new ActionNotPermittedInThisState();
    }
}
