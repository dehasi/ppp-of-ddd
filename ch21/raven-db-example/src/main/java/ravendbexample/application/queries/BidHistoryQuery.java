package ravendbexample.application.queries;

import ravendbexample.model.bidHistory.Bid;
import ravendbexample.model.bidHistory.IBidHistoryRepository;

import java.util.List;


public class BidHistoryQuery {

    private final IBidHistoryRepository bidHistoryRepository;

    public BidHistoryQuery(IBidHistoryRepository bidHistoryRepository) {this.bidHistoryRepository = bidHistoryRepository;}

    public List<BidInformation> bidHistoryFor(String auctionId) {
        var bidHistory = bidHistoryRepository.findBy(auctionId);

        return convert(bidHistory.bids());
    }

    public static List<BidInformation> convert(List<Bid> bids) {
        return bids.stream()
                .map(bid -> new BidInformation(bid.bidderId(), bid.amountBid().value(), bid.timeOfBid()))
                .toList();
    }
}
