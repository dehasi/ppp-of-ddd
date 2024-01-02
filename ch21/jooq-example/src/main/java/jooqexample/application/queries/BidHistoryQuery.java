package jooqexample.application.queries;


import jooqexample.model.bidHistory.Bid;
import jooqexample.model.bidHistory.IBidHistoryRepository;

import java.util.List;
import java.util.UUID;

public class BidHistoryQuery {

    private final IBidHistoryRepository bidHistoryRepository;

    public BidHistoryQuery(IBidHistoryRepository bidHistoryRepository) {this.bidHistoryRepository = bidHistoryRepository;}

    public List<BidInformation> bidHistoryFor(UUID auctionId) {
        var bidHistory = bidHistoryRepository.findBy(auctionId);

        return convert(bidHistory.bids());
    }

    private static List<BidInformation> convert(List<Bid> bids) {
        return bids.stream()
                .map(bid -> new BidInformation(bid.bidderId(), bid.amountBid().value(), bid.timeOfBid()))
                .toList();
    }
}
