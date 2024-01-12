package ch26reporting.domain.dealership;

import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.Dealership;

import java.util.List;

public interface IDealershipRepository {

    Dealership get(int dealershipId);

    List<Dealership> get(List<Integer> dealershipIds);
}
