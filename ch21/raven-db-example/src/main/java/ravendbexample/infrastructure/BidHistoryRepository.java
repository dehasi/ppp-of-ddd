package ravendbexample.infrastructure;

import net.ravendb.client.documents.session.IDocumentSession;
import ravendbexample.infrastructure.BidHistory_NumberOfBids.ReduceResult;
import ravendbexample.model.bidHistory.Bid;
import ravendbexample.model.bidHistory.BidHistory;
import ravendbexample.model.bidHistory.IBidHistoryRepository;


public class BidHistoryRepository implements IBidHistoryRepository {

    private final IDocumentSession documentSession;

    public BidHistoryRepository(IDocumentSession documentSession) {this.documentSession = documentSession;}

    @Override public int noOfBidsFor(String auctionId) {
        var count = documentSession.query(ReduceResult.class, BidHistory_NumberOfBids.class)
                .waitForNonStaleResults()
                .whereEquals("auctionId", auctionId)
                .firstOrDefault();

        return count.count();
    }

    @Override public void add(Bid bid) {
        documentSession.store(bid);
    }

    @Override public BidHistory findBy(String auctionId) {
        var bids = documentSession.query(Bid.class)
                .waitForNonStaleResults()
                .whereEquals("auctionId", auctionId)
                .toList();

        return new BidHistory(bids);
    }
}
