package hibernateexample.application.queries;

import hibernateexample.infrastructure.Clock;
import hibernateexample.model.bidHistory.IBidHistoryRepository;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static zeliba.the.TheComparable.the;

public class AuctionStatusQuery {

    private final Session session;
    private final IBidHistoryRepository bidHistoryRepository;
    private final Clock clock;

    public AuctionStatusQuery(Session session, IBidHistoryRepository bidHistoryRepository, Clock clock) {
        this.session = session;
        this.bidHistoryRepository = bidHistoryRepository;
        this.clock = clock;
    }

    public AuctionStatus auctionStatus(UUID auctionId) {
        AuctionStatus status = session
                .createNativeQuery(String.format("""
                        SELECT
                            id AS id,
                            current_price AS currentPrice,
                            bidder_member_id AS winningBidderId,
                            auction_ends AS auctionEnds
                        FROM auctions
                        WHERE id = '%s'
                            """, auctionId), AuctionStatus.class)
                .setResultListTransformer(Transformers.aliasToBean(AuctionStatus.class))
                .uniqueResult();

        status.timeRemaining = timeRemaining(status.auctionEnds);
        status.numberOfBids = bidHistoryRepository.noOfBidsFor(auctionId);

        return status;
    }

    public Duration timeRemaining(LocalDateTime auctionEnds) {
        if (the(clock.time()).isLessThan(auctionEnds))
            return Duration.between(auctionEnds, clock.time());
        else
            return Duration.ZERO;
    }
}
