package ch26reporting.application.dealiership.service;

import ch26reporting.application.dealiership.model.DealershipPerformanceReport;
import ch26reporting.application.dealiership.model.DealershipPerformanceStatus;
import ch26reporting.domain.dealership.IDealershipPerformanceTargetsProvider;
import ch26reporting.domain.dealership.IDealershipRepository;
import ch26reporting.domain.dealership.IDealershipRevenueCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Application Service
@Service
public class DealershipPerformanceReportBuilder {

    private final IDealershipRepository repository;
    private final IDealershipRevenueCalculator calculator;
    private final IDealershipPerformanceTargetsProvider provider;

    public DealershipPerformanceReportBuilder(
            IDealershipRepository repository,
            IDealershipRevenueCalculator calculator,
            IDealershipPerformanceTargetsProvider provider) {

        this.repository = repository;
        this.calculator = calculator;
        this.provider = provider;
    }

    public DealershipPerformanceReport BuildReport(List<Integer> dealershipIds, LocalDate start, LocalDate end) {
        var statuses = BuildStatuses(dealershipIds, start, end);

        return new DealershipPerformanceReport(start, end, statuses);
    }

    private List<DealershipPerformanceStatus> BuildStatuses(List<Integer> dealershipIds, LocalDate start, LocalDate end) {
        var statuses = new ArrayList<DealershipPerformanceStatus>();
        for (var id : dealershipIds) {
            // select N+1 - potentially bad for performance and efficiency
            // re-using existing domain code - quick to implement
            var dealership = repository.get(id);
            var targets = provider.Get(dealership, start, end);
            var actuals = calculator.CalculateFor(dealership, start, end);

            // map from domain to view model so UI is not coupled to domain objects
            // could move this logic into a separate mapper
            statuses.add(new DealershipPerformanceStatus(
                    dealership.name(),
                    actuals.TotalRevenue,
                    targets.TargetRevenue,
                    actuals.NetProfit,
                    targets.TargetProfit)
            );
        }
        return statuses;
    }
}
