package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

class InOven implements OnlineTakeawayOrderState {
    private final OnlineTakeawayOrder order;

    InOven(OnlineTakeawayOrder order) {
        this.order = order;
    }

    @Override public OnlineTakeawayOrderState cook() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState takeOutOfOven() {
        return new Cooked(order);
    }

    @Override public OnlineTakeawayOrderState toPackage() {
        throw new ActionNotPermittedInThisState();
    }

    @Override public OnlineTakeawayOrderState deliver() {
        throw new ActionNotPermittedInThisState();
    }
}
