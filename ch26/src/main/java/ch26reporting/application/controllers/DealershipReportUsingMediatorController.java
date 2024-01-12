package ch26reporting.application.controllers;

import ch26reporting.application.dealiership.model.DealershipPerformanceReport;
import ch26reporting.application.dealiership.model.DealershipPerformanceStatus;
import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.Dealership;
import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.IDealershipAssessment;
import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.IDealershipPerformanceTargetsProvider;
import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.IDealershipRevenueCalculatorForMediator;
import ch26reporting.domain.dealership.IDealershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dealership-report-using-mediator")
public class DealershipReportUsingMediatorController {

    private final IDealershipRepository repository;
    private final DealershipPerformanceReportBuilderUsingMediator builder;

    public DealershipReportUsingMediatorController(IDealershipRepository repository, DealershipPerformanceReportBuilderUsingMediator builder) {
        this.repository = repository;
        this.builder = builder;
    }

    @GetMapping DealershipPerformanceReport index(
            @RequestParam("ids") List<Integer> dealershipIds,
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        var dealerships = repository.get(dealershipIds);
        var viewModel = builder.BuildReport(dealerships, start, end);

        return viewModel;
    }

    @Service
    static class DealershipPerformanceReportBuilderUsingMediator {
        public DealershipPerformanceReportBuilderUsingMediator(IDealershipRevenueCalculatorForMediator calculator, IDealershipPerformanceTargetsProvider provider) {
            this.calculator = calculator;
            this.provider = provider;
        }

        private final IDealershipRevenueCalculatorForMediator calculator;
        private final IDealershipPerformanceTargetsProvider provider;

        public DealershipPerformanceReport BuildReport(List<Dealership> dealerships, LocalDate start, LocalDate end) {
            var statuses = BuildStatuses(dealerships, start, end);

            return new DealershipPerformanceReport(start, end, statuses);
        }

        private List<DealershipPerformanceStatus> BuildStatuses(List<Dealership> dealerships, LocalDate start, LocalDate end) {
            var statuses = new ArrayList<DealershipPerformanceStatus>();
            for (var dealership : dealerships) {
                var status = new DealershipPerformanceStatus();
                var mediator = new DealershipAssessmentMediator(status); // Mediator wraps status

                var targets = provider.Get(dealership, start, end);
                var actuals = calculator.CalculateFor(dealership, start, end);

                // pass in the mediator, so private data can be set on the media
                targets.Populate(mediator);
                actuals.Populate(mediator);

                statuses.add(status); // values will have been set by the mediator when passed into domain objects
            }
            return statuses;
        }
    }

    // "mediator" suffix on class name used for demo clarity
    public static class DealershipAssessmentMediator implements IDealershipAssessment {
        private final DealershipPerformanceStatus status;

        public DealershipAssessmentMediator(DealershipPerformanceStatus status) {
            this.status = status;
        }

        @Override public int TotalRevenue() {
            return status.TotalRevenue;
        }

        @Override public void TotalRevenue(int TotalRevenue) {
            status.TotalRevenue = TotalRevenue;
        }

        @Override public int TargetRevenue() {
            return status.TargetRevenue;
        }

        @Override public void TargetRevenue(int TargetRevenue) {
            status.TotalRevenue = TargetRevenue;
        }

        @Override public int NetProfit() {
            return status.NetProfit;
        }

        @Override public void NetProfit(int NetProfit) {
            status.NetProfit = NetProfit;
        }

        @Override public int TargetProfit() {
            return status.TargetProfit;
        }

        @Override public void TargetProfit(int TargetProfit) {
            status.TargetProfit = TargetProfit;
        }
    }
}
