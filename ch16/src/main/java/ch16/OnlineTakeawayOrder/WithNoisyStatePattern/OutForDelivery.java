package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

class OutForDelivery implements OnlineTakeawayOrderState {

    private final OnlineTakeawayOrder order;

    public OutForDelivery(OnlineTakeawayOrder order) {
        this.order = order;
    }

    @Override public OnlineTakeawayOrderState cook() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState takeOutOfOven() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState toPackage() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState deliver() {
        return new Delivered(order);
    }
}
