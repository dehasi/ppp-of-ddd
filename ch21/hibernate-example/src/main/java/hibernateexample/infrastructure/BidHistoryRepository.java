package hibernateexample.infrastructure;

import hibernateexample.model.bidHistory.Bid;
import hibernateexample.model.bidHistory.IBidHistoryRepository;
import org.hibernate.Session;

import java.util.UUID;

public class BidHistoryRepository implements IBidHistoryRepository {

    private final Session session;

    public BidHistoryRepository(Session session) {this.session = session;}


    @Override public int noOfBidsFor(UUID auctionId) {
        var sql = String.format("SELECT count(*) FROM bid_history WHERE auction_id = '%s'", auctionId);

        var query = session.createNativeQuery(sql, Integer.class);

        return query.uniqueResult();
    }

    @Override public void add(Bid bid) {
        session.save(bid);
    }
}
