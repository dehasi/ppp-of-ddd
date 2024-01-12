package ch26reporting.infrastructure.dealership;

import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator;
import ch26reporting.domain.dealership.DealershipPerformanceTargets;
import ch26reporting.domain.dealership.IDealershipPerformanceTargetsProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class DealershipPerformanceTargetsProvider implements IDealershipPerformanceTargetsProvider {
    @Override public DealershipPerformanceTargets Get(DealershipPerformanceReportDomainForMediator.Dealership dealership, LocalDate start, LocalDate end) {
        return new DealershipPerformanceTargets() {{
            TargetRevenue = 12;
            TargetProfit = 14;
        }};
    }
}
