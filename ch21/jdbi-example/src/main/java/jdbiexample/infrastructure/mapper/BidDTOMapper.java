package jdbiexample.infrastructure.mapper;

import jdbiexample.infrastructure.datamodel.BidDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidDTOMapper implements RowMapper<BidDTO> {

    @Override public BidDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        BidDTO bidDTO = new BidDTO();

        bidDTO.id = rs.getString("id");
        bidDTO.auctionId = rs.getString("auction_id");
        bidDTO.bidderId = rs.getString("bidder_id");
        bidDTO.bid = rs.getDouble("bid");
        bidDTO.timeOfBid = rs.getDate("time_of_bid").toLocalDate().atTime(rs.getTime("time_of_bid").toLocalTime());

        return bidDTO;
    }
}
