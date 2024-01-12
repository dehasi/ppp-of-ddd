package ch26reporting.domain.dealership;

import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.Dealership;

import java.time.LocalDate;

public interface IDealershipRevenueCalculator {
    DealershipPerformanceActuals CalculateFor(Dealership dealership, LocalDate start, LocalDate end);
}
