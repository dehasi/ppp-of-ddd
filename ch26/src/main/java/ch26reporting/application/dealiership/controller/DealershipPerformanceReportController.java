package ch26reporting.application.dealiership.controller;

import ch26reporting.application.dealiership.model.DealershipPerformanceReport;
import ch26reporting.application.dealiership.service.DealershipPerformanceReportBuilder;
import ch26reporting.domain.dealership.IDealershipPerformanceTargetsProvider;
import ch26reporting.domain.dealership.IDealershipRepository;
import ch26reporting.domain.dealership.IDealershipRevenueCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dealership-performance-report")
class DealershipPerformanceReportController {

    private final DealershipPerformanceReportBuilder builder;

    public DealershipPerformanceReportController(
            IDealershipRepository repository,
            IDealershipRevenueCalculator calculator,
            IDealershipPerformanceTargetsProvider provider) {

        this.builder = new DealershipPerformanceReportBuilder(repository, calculator, provider);
    }

    @GetMapping
    public DealershipPerformanceReport index(
            @RequestParam("ids") List<Integer> dealershipIds,
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {
        return builder.BuildReport(dealershipIds, start, end);
    }

}
