package ch26reporting.application.dealiership.model;

import java.time.LocalDate;
import java.util.List;

// view model
public record DealershipPerformanceReport(
        LocalDate ReportStartDate,
        LocalDate ReportEndDate,
        List<DealershipPerformanceStatus> Dealerships
) {}
