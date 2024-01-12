package ch26reporting.domain.dealership;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

//namespace
public class DealershipPerformanceReportDomainForMediator {

    // mediator interface - stable domain structure that can be exposed
    public interface IDealershipAssessment {
        int TotalRevenue();

        void TotalRevenue(int TotalRevenue);

        int TargetRevenue();

        void TargetRevenue(int TargetRevenue);

        int NetProfit();

        void NetProfit(int NetProfit);

        int TargetProfit();

        void TargetProfit(int TargetProfit);
    }

    public interface IDealershipRevenueCalculatorForMediator {
        DealershipPerformanceActualsForMediator CalculateFor(Dealership dealership, LocalDate start, LocalDate end);
    }

    @Service
    static class DealershipRevenueCalculatorForMediator implements IDealershipRevenueCalculatorForMediator {

        @Override public DealershipPerformanceActualsForMediator CalculateFor(Dealership dealership, LocalDate start, LocalDate end) {
            return new DealershipPerformanceActualsForMediator() {{
                this.totalRevenue = 1212;
                this.netProfit = 1212;
            }};
        }
    }

    public interface IDealershipPerformanceTargetsProvider {
        DealershipPerformanceTargets Get(Dealership dealership, LocalDate start, LocalDate end);
    }

    @Service
    static class DealershipPerformanceTargetsProvider implements IDealershipPerformanceTargetsProvider {

        @Override public DealershipPerformanceTargets Get(Dealership dealership, LocalDate start, LocalDate end) {
            return new DealershipPerformanceTargets() {{
                targetRevenue = 100;
                targetProfit = 200;
            }};
        }
    }

    public static class DealershipPerformanceTargets {
        // private fields hide potentially volatile domain structure
        protected int targetRevenue;
        protected int targetProfit;

        public void Populate(IDealershipAssessment mediator) {
            mediator.TargetRevenue(targetRevenue);
            mediator.TargetProfit(targetProfit);
        }
    }

    public static class DealershipPerformanceActualsForMediator {
        // private fields hide potentially volatile domain structure
        protected int totalRevenue;
        protected int netProfit;

        public void Populate(IDealershipAssessment mediator) {
            mediator.TotalRevenue(totalRevenue);
            mediator.NetProfit(netProfit);
        }
    }

    public static class DealershipPerformance {
        // private fields hide potentially volatile domain structure
        private int totalRevenue;
        private int netProfit;

        public void Populate(IDealershipAssessment mediator) {
            mediator.TotalRevenue(totalRevenue);
            mediator.NetProfit(netProfit);
        }
    }

    public record Dealership(
            int id,
            String name
    ) {}
}
