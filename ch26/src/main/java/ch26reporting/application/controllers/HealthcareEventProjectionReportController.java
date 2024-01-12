package ch26reporting.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;

@RestController
@RequestMapping("/healthcare-projection-report")
class HealthcareEventProjectionReportController {

    @Autowired HealthcareReportBuilder healthcareReportBuilder;

    @GetMapping HealthcareReport index(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end,
            @RequestParam("ids") List<String> diagnosisIds) {
        var reportViewModel = healthcareReportBuilder.Build(start, end, diagnosisIds);

        // Optional: Create a view and display the report
        return reportViewModel;
    }

    // Report building Application Service
    @Service
    static class HealthcareReportBuilder {
        @Autowired
        private RestTemplate esRestTemplate;
        @Autowired
        private ObjectMapper objectMapper;

        public HealthcareReport Build(LocalDate start, LocalDate end, List<String> diagnosisIds) {
            var monthsInReport = getMonthsInRange(start, end); // report columns
            var monthlyOverallTotals = fetchMonthlyTotalsFromES(monthsInReport); // used to calculate percentages
            var queries = buildQueriesFor(monthsInReport, diagnosisIds); // queries for ES
            var summaries = BuildMonthlySummariesFor(queries, monthlyOverallTotals); // use queries

            return new HealthcareReport(start,end,summaries);
        }

        private List<DiagnosisSummary> BuildMonthlySummariesFor(List<DiagnosisQuery> queries,
                                                                Map<LocalDate, Integer> monthlyTotals) {
            List<DiagnosisSummary> result = new ArrayList<>();
            for (var q : queries) { // may want to run these in parallel for perf

                var diagnosisTotal = FetchTotalFromESFor(q);
                var monthTotal = monthlyTotals.getOrDefault(q.Month, 0);
                var percent = monthTotal == 0 ? 0 : ((double) diagnosisTotal / monthTotal) * 100;
                // yield
                result.add(new DiagnosisSummary() {{
                    Amount = diagnosisTotal;
                    DiagnosisName = GetDiagnosisName(q.DiagnosisId);
                    Month = q.Month;
                    Percentage = percent;
                    MonthString = q.Month.toString(); //("yyyy/MM")
                }});
            }
            return result;
        }

        private int FetchTotalFromESFor(DiagnosisQuery query) {
            // don't hard-code this URL. Access via entry point resource
            var projectionStateUrl = "http://localhost:2113/projection/DiagnosesByMonthCounts/state";
            var streamName = "diagnoses-" + query.DiagnosisId + "_" + query.Month.format(DateTimeFormatter.ofPattern("yyyyMM"));  //"yyyyMM"
            final var url = projectionStateUrl + "?partition=" + streamName;
            // may want to use caching here
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            var response = esRestTemplate.exchange(url, GET, entity, DiagnosisCount.class);

            return response.getBody() == null ? 0 : response.getBody().count();
        }

        private Map<LocalDate, Integer> fetchMonthlyTotalsFromES(List<LocalDate> months) {
            // don't hard-code this URL. Access via entry point resource
            var projectionStateUrl = "http://localhost:2113/projection/MonthsCounts/state";

            var totals = new HashMap<LocalDate, Integer>();
            for (var m : months) {
                var streamName = "month-" + m.format(DateTimeFormatter.ofPattern("yyyyMM")); // "yyyyMM"
                var url = projectionStateUrl + "?partition=" + streamName;

                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", "*/*");
                HttpEntity<String> entity = new HttpEntity<>(null, headers);
                var response = esRestTemplate.exchange(url, GET, entity, DiagnosisCount.class);
                totals.put(m, response.getBody() == null ? 0 : response.getBody().count());
            }

            return totals;
        }

        private List<DiagnosisQuery> buildQueriesFor(List<LocalDate> months, List<String> diagnosisIds) {
            List<DiagnosisQuery> yieldResult = new ArrayList<>();
            for (var month : months) {
                for (var id : diagnosisIds) {
                    // yield
                    yieldResult.add(new DiagnosisQuery() {
                        {
                            DiagnosisId = id;
                            Month = month;
                        }
                    });
                }
            }
            return yieldResult;
        }

        // this would problably live inside a helper, and not in an application service
        private List<LocalDate> getMonthsInRange(LocalDate start, LocalDate end) {
            var startOfFirst = LocalDate.of(start.getYear(), start.getMonth(), 1);
            var lastOfEnd = LocalDate.of(end.getYear(), end.getMonth().plus(1), 1);

            List<LocalDate> yieldResult = new ArrayList<>();
            var current = startOfFirst;
            do {
                yieldResult.add(current);
                current = current.plusMonths(1);
            } while (current.isBefore(lastOfEnd));

            return yieldResult;
        }

        private static String GetDiagnosisName(String diagnosisId) {
            // many ways to implements this:
            // could come from an event stream
            // could live as a fixed list in cache if diagnosis never change 
            // could be a lookup from a datastore
            switch (diagnosisId) {
                case "dg1": {
                    return "Eczema";
                }
                case "dg2": {
                    return "Vertigo";
                }
                case "dg3": {
                    return "Hypochrondriac";
                }
                default: {
                    return "Unknown";
                }
            }
        }
    }

    // represents state projection
    record DiagnosisCount(int count) {}

    static class DiagnosisQuery {
        public LocalDate Month;

        public String DiagnosisId;
    }


    // report view models
    static record HealthcareReport(
            LocalDate start,
            LocalDate end,
            List<DiagnosisSummary> summaries
    ) {}

    static class DiagnosisSummary {
        public String DiagnosisName;

        public LocalDate Month;

        public String MonthString;

        public int Amount;

        public double Percentage;
    }
}
