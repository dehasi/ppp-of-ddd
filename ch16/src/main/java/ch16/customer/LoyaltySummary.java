package ch16.customer;

public record LoyaltySummary(LoyaltyStatus status, int point) {

    public enum LoyaltyStatus {
        Gold,
        Silver,
        Bronze
    }
}
