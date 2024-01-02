package jdbiexample.infrastructure;

import jdbiexample.infrastructure.datamodel.BidDTO;
import jdbiexample.model.auction.Money;
import jdbiexample.model.bidHistory.Bid;
import jdbiexample.model.bidHistory.BidHistory;
import jdbiexample.model.bidHistory.IBidHistoryRepository;
import me.dehasi.replacements.exception.NotImplementedException;
import org.jdbi.v3.core.Handle;

import java.util.List;
import java.util.UUID;


public class BidHistoryRepository implements IBidHistoryRepository, IUnitOfWorkRepository {

    private final IUnitOfWork unitOfWork;
    private final System system;

    public BidHistoryRepository(IUnitOfWork auctionDatabaseContext, System system) {
        this.unitOfWork = auctionDatabaseContext;
        this.system = system;
    }

    @Override public int noOfBidsFor(UUID auctionId) {
        try (Handle handle = system.handle()) {
            return handle.select("""
                            SELECT count(*)
                            FROM bids
                            WHERE auction_id = :auction_id
                            """)
                    .bind("auction_id", auctionId)
                    .mapTo(Integer.class)
                    .findOne().get();
        }
    }

    @Override public void add(Bid bid) {
        var bidHistoryDTO = new BidDTO();
        bidHistoryDTO.auctionId = bid.auctionId().toString();
        bidHistoryDTO.bid = bid.amountBid().getSnapshot().value;
        bidHistoryDTO.bidderId = bid.bidderId().toString();
        bidHistoryDTO.timeOfBid = bid.timeOfBid();
        bidHistoryDTO.id = UUID.randomUUID().toString();

        unitOfWork.registerNew(bidHistoryDTO, this);
    }

    @Override public BidHistory findBy(UUID auctionId) {
        List<BidDTO> bidDTOS;
        try (var handle = system.handle()) {
            bidDTOS = handle.select("""
                            SELECT *
                            FROM bids
                            WHERE auction_id = :auction_id
                            """)
                    .bind("auction_id", auctionId)
                    .mapTo(BidDTO.class)
                    .list();
        }
        List<Bid> bids = bidDTOS.stream()
                .map(bidDTO -> new Bid(
                        UUID.fromString(bidDTO.auctionId),
                        UUID.fromString(bidDTO.bidderId),
                        new Money(bidDTO.bid),
                        bidDTO.timeOfBid))
                .toList();
        return new BidHistory(bids);
    }

    @Override public void persistCreationOf(IAggregateDataModel entity) {
        var bidHistoryDTO = (BidDTO) entity;

        var recordsAdded = system.inTransaction().createUpdate("""
                        INSERT INTO bids (id,  bid,  auction_id,  bidder_id,  time_of_bid)
                                 VALUES (:id, :bid, :auction_id, :bidder_id, :time_of_bid)
                        """)
                .bind("id", bidHistoryDTO.id)
                .bind("bid", bidHistoryDTO.bid)
                .bind("auction_id", bidHistoryDTO.auctionId)
                .bind("bidder_id", bidHistoryDTO.bidderId)
                .bind("time_of_bid", bidHistoryDTO.timeOfBid)
                .execute();
    }

    @Override public void persistUpdateOf(IAggregateDataModel entity) {
        throw new NotImplementedException();
    }
}
