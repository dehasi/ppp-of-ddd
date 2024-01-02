package jooqexample.infrastructure;

import jooqexample.infrastructure.jooq.tables.records.AuctionsRecord;
import jooqexample.model.auction.Auction;
import jooqexample.model.auction.AuctionSnapshot;
import jooqexample.model.auction.IAuctionRepository;
import jooqexample.model.auction.WinningBidSnapshot;
import org.jooq.DSLContext;

import java.util.UUID;

import static jooqexample.infrastructure.jooq.tables.Auctions.AUCTIONS;

public class AuctionRepository implements IAuctionRepository {

    private final DSLContext auctionDatabaseContext;

    public AuctionRepository(DSLContext auctionDatabaseContext) {this.auctionDatabaseContext = auctionDatabaseContext;}

    public void add(Auction auction) {
        var auctionsRecord = auctionDatabaseContext.newRecord(AUCTIONS);

        map(auctionsRecord, auction.getSnapshot());

        auctionsRecord.store();
    }

    @Override public void save(Auction auction) {
        var auctionsRecord = auctionDatabaseContext.fetchOne(AUCTIONS, AUCTIONS.ID.eq(auction.id.toString()));

        map(auctionsRecord, auction.getSnapshot());

        auctionsRecord.store();
    }

    public Auction findBy(UUID id) {
        AuctionsRecord auctionsRecord = auctionDatabaseContext.fetchOne(AUCTIONS, AUCTIONS.ID.eq(id.toString()));
        var auctionSnapshot = new AuctionSnapshot();

        auctionSnapshot.id = UUID.fromString(auctionsRecord.getId());
        auctionSnapshot.endsAt = auctionsRecord.getAuctionEnds();
        auctionSnapshot.startingPrice = auctionsRecord.getBid();
        // auctionSnapshot.version = auctionsRecord.getVersion; // ??

        if (auctionsRecord.getBidderMemberId() != null) {
            var bidSnapshot = new WinningBidSnapshot();

            bidSnapshot.biddersMaximumBid = auctionsRecord.getMaximumBid();
            bidSnapshot.currentPrice = auctionsRecord.getCurrentPrice();
            bidSnapshot.biddersId = UUID.fromString(auctionsRecord.getBidderMemberId());
            bidSnapshot.timeOfBid = auctionsRecord.getTimeOfBid();
            auctionSnapshot.winningBid = bidSnapshot;
        }

        return Auction.createFrom(auctionSnapshot);
    }

    private void map(AuctionsRecord auctionsRecord, AuctionSnapshot snapshot) {
        auctionsRecord.setId(snapshot.id.toString());
        auctionsRecord.setBid(snapshot.startingPrice);
        auctionsRecord.setAuctionEnds(snapshot.endsAt);
        // auctionsRecord. (snapshot.version);

        if (snapshot.winningBid != null) {
            auctionsRecord.setBidderMemberId(snapshot.winningBid.biddersId.toString());
            auctionsRecord.setCurrentPrice(snapshot.winningBid.currentPrice);
            auctionsRecord.setMaximumBid(snapshot.winningBid.biddersMaximumBid);
            auctionsRecord.setTimeOfBid(snapshot.winningBid.timeOfBid);
        }
    }
}
