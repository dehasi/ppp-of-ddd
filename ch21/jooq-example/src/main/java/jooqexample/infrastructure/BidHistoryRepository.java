package jooqexample.infrastructure;

import jooqexample.infrastructure.jooq.tables.records.BidsRecord;
import jooqexample.model.auction.Money;
import jooqexample.model.bidHistory.Bid;
import jooqexample.model.bidHistory.BidHistory;
import jooqexample.model.bidHistory.IBidHistoryRepository;
import org.jooq.DSLContext;
import org.jooq.Result;

import java.util.UUID;

import static jooqexample.infrastructure.jooq.tables.Bids.BIDS;

public class BidHistoryRepository implements IBidHistoryRepository {

    private final DSLContext auctionDatabaseContext;

    public BidHistoryRepository(DSLContext auctionDatabaseContext) {this.auctionDatabaseContext = auctionDatabaseContext;}

    @Override public int noOfBidsFor(UUID auctionId) {
        return auctionDatabaseContext.fetchCount(BIDS, BIDS.AUCTION_ID.eq(auctionId.toString()));
    }

    @Override public void add(Bid bid) {
        var bidsRecord = auctionDatabaseContext.newRecord(BIDS);
        bidsRecord.setId(UUID.randomUUID().toString());
        bidsRecord.setAuctionId(bid.auctionId().toString());
        bidsRecord.setBid(bid.amountBid().getSnapshot().value);
        bidsRecord.setBidderId(bid.bidderId().toString());
        bidsRecord.setTimeOfBid(bid.timeOfBid());
        bidsRecord.store();
    }

    @Override public BidHistory findBy(UUID auctionId) {
        Result<BidsRecord> bidsRecords = auctionDatabaseContext.fetch(BIDS, BIDS.AUCTION_ID.eq(auctionId.toString()));

        var bids = bidsRecords.stream()
                .map(bidRecord -> new Bid(
                        UUID.fromString(bidRecord.getAuctionId()),
                        UUID.fromString(bidRecord.getBidderId()),
                        new Money(bidRecord.getBid()),
                        bidRecord.getTimeOfBid()))
                .toList();

        return new BidHistory(bids);
    }
}
