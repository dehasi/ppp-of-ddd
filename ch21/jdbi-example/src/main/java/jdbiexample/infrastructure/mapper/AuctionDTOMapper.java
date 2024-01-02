package jdbiexample.infrastructure.mapper;

import jdbiexample.infrastructure.datamodel.AuctionDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionDTOMapper implements RowMapper<AuctionDTO> {

    @Override public AuctionDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        AuctionDTO auctionDTO = new AuctionDTO();

        auctionDTO.id = rs.getString("id");
        auctionDTO.version = rs.getInt("version");
        auctionDTO.startingPrice = rs.getDouble("starting_price");
        auctionDTO.auctionEnds = rs.getDate("auction_ends").toLocalDate().atTime(rs.getTime("auction_ends").toLocalTime());

        // nullable
        if (rs.getString("bidder_member_id") != null)
            auctionDTO.bidderMemberId = rs.getString("bidder_member_id");

        if (rs.getDate("time_of_bid") != null)
            auctionDTO.timeOfBid = rs.getDate("time_of_bid").toLocalDate().atTime(rs.getTime("time_of_bid").toLocalTime());

        auctionDTO.maximumBid = rs.getDouble("maximum_bid");
        if (rs.wasNull()) auctionDTO.maximumBid = null;

        auctionDTO.currentPrice = rs.getDouble("current_price");
        if (rs.wasNull()) auctionDTO.currentPrice = null;

        return auctionDTO;
    }
}
