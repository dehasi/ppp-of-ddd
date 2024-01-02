package hibernateexample.application.queries;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.UUID;

public class BidHistoryQuery {

    private final Session session;

    public BidHistoryQuery(Session session) {this.session = session;}

    public List<BidInformation> bidHistoryFor(UUID auctionId) {
        var status = session
                .createNativeQuery(String.format("""
                        SELECT
                            bidder_id AS bidder,
                            bid AS amountBid,
                            time_of_bid
                        FROM bid_history
                        WHERE auction_id = '%s'
                        ORDER BY bid DESC, time_of_bid ASC
                        """, auctionId), BidInformation.class)
                .setResultListTransformer(Transformers.aliasToBean(BidInformation.class));

        return status.getResultList();
    }
}
