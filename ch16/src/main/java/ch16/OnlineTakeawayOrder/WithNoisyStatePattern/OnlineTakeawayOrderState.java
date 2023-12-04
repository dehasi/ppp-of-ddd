package ch16.OnlineTakeawayOrder.WithNoisyStatePattern;

interface OnlineTakeawayOrderState {

    OnlineTakeawayOrderState cook();

    OnlineTakeawayOrderState takeOutOfOven();

    OnlineTakeawayOrderState toPackage();

    OnlineTakeawayOrderState deliver();
}
