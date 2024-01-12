package ch26reporting.domain.dealership;

import java.time.LocalDate;

public interface IDealershipPerformanceTargetsProvider {
    DealershipPerformanceTargets Get(DealershipPerformanceReportDomainForMediator.Dealership dealership, LocalDate start, LocalDate end);
}
