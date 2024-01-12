package ch26reporting.application.controllers;

import ch26reporting.domain.loyalty.LoyaltyDomainNamespace.LoyaltyReport;
import ch26reporting.domain.loyalty.LoyaltyDomainNamespace.LoyaltySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/denormalized-loyalty-report")
class DenormalizedLoyaltyReportController {

    @Autowired DenormalizedLoyaltyReportBuilder loyaltyReportBuilder;

    @GetMapping LoyaltyReport index(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        return loyaltyReportBuilder.build(start, end);
    }

    @Service
    static class DenormalizedLoyaltyReportBuilder {
        @Autowired NamedParameterJdbcTemplate jdbcTemplate;

        public LoyaltyReport build(LocalDate start, LocalDate end) {
            List<LoyaltySummary> summaries = null;
            var query = """
                    SELECT `month`, points_per_dollar, net_profit, signups, purchases
                    FROM denormalized_loyalty_report_view_cache
                    WHERE `month` >= :start
                    AND `month` < :end
                    """;
            summaries = jdbcTemplate.query(query, Map.of("start", start, "end", end), (rs, __) ->
                    new LoyaltySummary(
                            rs.getDate("month").toLocalDate(),
                            rs.getInt("points_per_dollar"),
                            rs.getDouble("net_profit"),
                            rs.getInt("signups"),
                            rs.getInt("purchases")
                    ));

            return new LoyaltyReport(summaries);
        }
    }
}
