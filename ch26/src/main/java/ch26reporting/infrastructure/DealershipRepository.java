package ch26reporting.infrastructure;

import ch26reporting.domain.dealership.DealershipPerformanceReportDomainForMediator.Dealership;
import ch26reporting.domain.dealership.IDealershipRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
class DealershipRepository implements IDealershipRepository {

    private static final RowMapper<Dealership> ROW_MAPPER = (rs, rowNum) -> new Dealership(rs.getInt("id"), rs.getString("name"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    DealershipRepository(NamedParameterJdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override public Dealership get(int dealershipId) {
        return jdbcTemplate.queryForObject("""
                        SELECT *
                        FROM dealerships
                        WHERE id = :id
                        """,
                Map.of("id", dealershipId),
                ROW_MAPPER);
    }

    @Override public List<Dealership> get(List<Integer> dealershipIds) {
        return jdbcTemplate.query("""
                        SELECT *
                        FROM dealerships
                        WHERE id IN (:ids)
                        """,
                Map.of("ids", dealershipIds),
                ROW_MAPPER);
    }
}
