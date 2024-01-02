package jdbiexample.infrastructure;

import jdbiexample.infrastructure.datamodel.AuctionDTO;
import jdbiexample.model.auction.Auction;
import jdbiexample.model.auction.AuctionSnapshot;
import jdbiexample.model.auction.IAuctionRepository;
import jdbiexample.model.auction.WinningBidSnapshot;

import java.util.UUID;

public class AuctionRepository implements IAuctionRepository, IUnitOfWorkRepository {

    private final IUnitOfWork unitOfWork;
    private final System system;

    public AuctionRepository(IUnitOfWork unitOfWork, System system) {
        this.unitOfWork = unitOfWork;
        this.system = system;
    }

    public void add(Auction auction) {
        var snapshot = auction.getSnapshot();
        var auctionDTO = new AuctionDTO();

        map(auctionDTO, snapshot);

        unitOfWork.registerNew(auctionDTO, this);
    }

    @Override public void save(Auction auction) {
        var snapshot = auction.getSnapshot();
        var auctionDTO = new AuctionDTO();

        map(auctionDTO, snapshot);

        unitOfWork.registerAmended(auctionDTO, this);
    }

    @Override public Auction findBy(UUID id) {
        AuctionDTO auctionDTO;
        try (var handle = system.handle()) {
            auctionDTO = handle
                    .select("SELECT * FROM auctions WHERE id = :id")
                    .bind("id", id)
                    .mapTo(AuctionDTO.class)
                    .findOne().get();
        }

        var auctionSnapshot = new AuctionSnapshot();

        auctionSnapshot.id = UUID.fromString(auctionDTO.id);
        auctionSnapshot.endsAt = auctionDTO.auctionEnds;
        auctionSnapshot.startingPrice = auctionDTO.startingPrice;
        auctionSnapshot.version = auctionDTO.version;

        if (auctionDTO.bidderMemberId != null) {
            var bidSnapshot = new WinningBidSnapshot();

            bidSnapshot.biddersMaximumBid = auctionDTO.maximumBid;
            bidSnapshot.currentPrice = auctionDTO.currentPrice;
            bidSnapshot.biddersId = UUID.fromString(auctionDTO.bidderMemberId);
            bidSnapshot.timeOfBid = auctionDTO.timeOfBid;
            auctionSnapshot.winningBid = bidSnapshot;
        }

        return Auction.createFrom(auctionSnapshot);

    }

    @Override public void persistCreationOf(IAggregateDataModel entity) {
        var auctionDTO = (AuctionDTO) entity;

        var recordsAdded = system.inTransaction().createUpdate("""
                        INSERT INTO auctions
                               ( id,  version,  auction_ends,  bidder_member_id,  starting_price,  current_price,  maximum_bid,  time_of_bid)
                        VALUES (:id, :version, :auction_ends, :bidder_member_id, :starting_price, :current_price, :maximum_bid, :time_of_bid)
                        """)
                .bind("id", auctionDTO.id)
                .bind("version", auctionDTO.version)
                .bind("auction_ends", auctionDTO.auctionEnds)
                .bind("bidder_member_id", auctionDTO.bidderMemberId)
                .bind("starting_price", auctionDTO.startingPrice)
                .bind("current_price", auctionDTO.currentPrice)
                .bind("maximum_bid", auctionDTO.maximumBid)
                .bind("time_of_bid", auctionDTO.timeOfBid)
                .execute();

    }

    @Override public void persistUpdateOf(IAggregateDataModel entity) {
        var auctionDTO = (AuctionDTO) entity;

        var recordsUpdated = system.inTransaction().createUpdate("""
                        UPDATE auctions
                        SET
                            id = :id,
                            version = :version,
                            auction_ends = :auction_ends,
                            bidder_member_id = :bidder_member_id,
                            starting_price = :starting_price,
                            current_price = :current_price,
                            maximum_bid = :maximum_bid,
                            time_of_bid = :time_of_bid
                        WHERE
                            id = :id AND version = :previous_version
                        """)
                .bind("id", auctionDTO.id)
                .bind("version", auctionDTO.version + 1)
                .bind("previous_version", auctionDTO.version)
                .bind("auction_ends", auctionDTO.auctionEnds)
                .bind("bidder_member_id", auctionDTO.bidderMemberId)
                .bind("starting_price", auctionDTO.startingPrice)
                .bind("current_price", auctionDTO.currentPrice)
                .bind("maximum_bid", auctionDTO.maximumBid)
                .bind("time_of_bid", auctionDTO.timeOfBid)
                .execute();

        if (recordsUpdated != 1) {
            throw new ConcurrencyException("Expected recordsUpdated: 1, but was " + recordsUpdated);
        }
    }

    private void map(AuctionDTO auctionDTO, AuctionSnapshot snapshot) {
        auctionDTO.id = snapshot.id.toString();
        auctionDTO.version = snapshot.version;
        auctionDTO.startingPrice = snapshot.startingPrice;
        auctionDTO.auctionEnds = snapshot.endsAt;

        if (snapshot.winningBid != null) {
            auctionDTO.bidderMemberId = snapshot.winningBid.biddersId.toString();
            auctionDTO.currentPrice = snapshot.winningBid.currentPrice;
            auctionDTO.maximumBid = snapshot.winningBid.biddersMaximumBid;
            auctionDTO.timeOfBid = snapshot.winningBid.timeOfBid;
        }
    }
}
