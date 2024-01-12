package ch26reporting.infrastructure.dealership;

import ch26reporting.domain.dealership.DealershipPerformanceActuals;
import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator;
import ch26reporting.domain.dealership.IDealershipRevenueCalculator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DealershipRevenueCalculator implements IDealershipRevenueCalculator {
    @Override public DealershipPerformanceActuals CalculateFor(DealershipPerformanceReportDomainForMediator.Dealership dealership, LocalDate start, LocalDate end) {
        return new DealershipPerformanceActuals() {{
            TotalRevenue = 25;
            NetProfit = 52;
        }};
    }
}
