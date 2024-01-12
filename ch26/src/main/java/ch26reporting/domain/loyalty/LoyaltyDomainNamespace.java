package ch26reporting.domain.loyalty;

import java.time.LocalDate;
import java.util.List;

public class LoyaltyDomainNamespace {

    // view / presentation models
    public record LoyaltyReport(List<LoyaltySummary> summaries) {}

    public record LoyaltySummary(
            LocalDate month,
            int pointsPerDollar,
            double netProfit,
            int signUps,
            int purchases
    ) {}

    // database models
    public record LoyaltySettings(LocalDate month, int pointsPerDollar) {}

    public record SignupCount(LocalDate month, int signups) {}

    public record PurchasesAndProfit(
            LocalDate month,
            int purchases,
            double profit
    ) {}
}
