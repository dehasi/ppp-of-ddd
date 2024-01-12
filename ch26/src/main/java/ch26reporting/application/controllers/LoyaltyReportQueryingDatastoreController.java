package ch26reporting.application.controllers;

import ch26reporting.domain.loyalty.LoyaltyDomainNamespace.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loyalty-report-querying-datastore")
class LoyaltyReportQueryingDatastoreController {

    @Autowired LoyaltyReportBuilder loyaltyReportBuilder;

    @GetMapping LoyaltyReport index(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        var report = loyaltyReportBuilder.Build(start, end);

        return report;
    }

    // Application Service
    @Service
    static class LoyaltyReportBuilder {
        // see SportsStoreDatabase project in this solution
        @Autowired NamedParameterJdbcTemplate jdbcTemplate;

        private String connString = "";

        public LoyaltyReport Build(LocalDate start, LocalDate end) {
            List<PurchasesAndProfit> profits = null;
            List<SignupCount> signups = null;
            List<LoyaltySettings> settings = null;
            try {
                var pointsQuery = """
                        SELECT `month`, points_per_dollar
                        FROM loyalty_settings
                        WHERE `month` >= :start
                          AND `month` < :end""";
                //con.Query < LoyaltySettings > (pointsQuery, new {start = start, end = end});
                settings = jdbcTemplate.query(pointsQuery,
                        Map.of("start", start, "end", end),
                        (rs, rowNum) -> new LoyaltySettings(rs.getDate("month").toLocalDate(), rs.getInt("points_per_dollar")));

                var signupsQuery = """
                        SELECT count(*) AS signups, created_at AS `month`
                        FROM loyalty_accounts
                        WHERE is_active = true
                        AND created_at >= :start
                        AND created_at < :end
                        GROUP BY created_at""";
                //;con.Query < SignupCount > (signupsQuery, new {start = start, end = end} );
                signups = jdbcTemplate.query(signupsQuery,
                        Map.of("start", start, "end", end),
                        (rs, rowNum) -> new SignupCount(rs.getDate("month").toLocalDate(), rs.getInt("signups")));
                var profitQuery = """
                        SELECT
                              CONCAT(YEAR(o.`date`), '-', month(o.`date`), '-1') AS `month`,
                              (
                                  SELECT count(*)
                                  FROM orders
                                  WHERE `date` >= :start
                                  AND `date` < :end
                              ) AS purchases,
                              (SELECT ((sum(net_profit) / (
                                  SELECT sum(net_profit)
                                  FROM orders
                                  WHERE `date` >= :start
                                  AND `date` < :end
                              )) * 100)) as net_profit
                        FROM orders o
                        JOIN users u on o.user_id = u.id
                        JOIN loyalty_accounts la on u.id = la.user_id
                        WHERE la.is_active = 1
                        AND o.`date` >= :start
                        AND o.`date` < :end
                        GROUP BY CONCAT(YEAR(o.`date`), '-', month(o.`date`), '-1')
                        """;
                //con.Query < PurchasesAndProfit > (profitQuery, start = start, end = end);
                profits = jdbcTemplate.query(profitQuery,
                        Map.of("start", start, "end", end),
                        (rs, rowNum) -> new PurchasesAndProfit(
                                rs.getDate("month").toLocalDate(),
                                rs.getInt("purchases"),
                                rs.getDouble("net_profit")));
                return Map(profits, signups, settings, start, end);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private LoyaltyReport Map(List<PurchasesAndProfit> profits, List<SignupCount> signups,
                                  List<LoyaltySettings> loyaltySettings, LocalDate start, LocalDate end) {
            var summaries = new ArrayList<LoyaltySummary>();

            start = startOfMonth(start);
            end = startOfMonth(end);
            // Create a summary for each month in the report's range
            for (var month : monthsBetweenInclusive(start, end)) {
                var monthProfits = profits.stream().filter(s -> startOfMonth(s.month()).equals(month)).findFirst().orElse(null);
                if (monthProfits == null) continue;
                var monthSettings = loyaltySettings.stream().filter(s -> startOfMonth(s.month()).equals(month)).findFirst().orElse(null);
                if (monthSettings == null) continue;
                var monthSignups = signups.stream().filter(s -> startOfMonth(s.month()).equals(month)).findFirst().orElse(null);
                if (monthSignups == null) continue;

                var summary = new LoyaltySummary(
                        month,
                        monthSettings.pointsPerDollar(),
                        monthProfits.profit(),
                        monthProfits.purchases(),
                        monthSignups.signups()
                );
                summaries.add(summary);
            }

            return new LoyaltyReport(summaries);
        }

        private static List<LocalDate> monthsBetweenInclusive(LocalDate start, LocalDate end) {
            var firstMonth = startOfMonth(start);
            var lastMonth = startOfMonth(end);

            var months = new ArrayList<LocalDate>();

            var currentMonth = firstMonth;
            while (currentMonth.isBefore(lastMonth)) {
                months.add(startOfMonth(currentMonth));
                currentMonth = currentMonth.plusMonths(1);
            }

            return months;
        }

        private static LocalDate startOfMonth(LocalDate date) {
            return LocalDate.of(date.getYear(), date.getMonth(), 1);
        }
    }
}
