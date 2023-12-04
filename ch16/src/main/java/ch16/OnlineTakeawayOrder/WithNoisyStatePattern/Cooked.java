package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

class Cooked implements OnlineTakeawayOrderState {
    private final OnlineTakeawayOrder order;

    public Cooked(OnlineTakeawayOrder order) {this.order = order;}

    @Override public OnlineTakeawayOrderState cook() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState takeOutOfOven() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState toPackage() {
        return new OutForDelivery(order);
    }

    @Override public OnlineTakeawayOrderState deliver() {
        throw new ActionNotPermittedInThisState();
    }
}
