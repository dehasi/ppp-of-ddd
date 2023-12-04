package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

class Delivered implements OnlineTakeawayOrderState {

    private final OnlineTakeawayOrder order;

    public Delivered(OnlineTakeawayOrder order) {
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
        throw new ActionNotPermittedInThisState();
    }
}
