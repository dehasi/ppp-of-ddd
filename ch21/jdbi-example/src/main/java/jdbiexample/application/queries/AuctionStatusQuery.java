package jdbiexample.application.queries;

import jdbiexample.infrastructure.Clock;
import jdbiexample.model.auction.AuctionSnapshot;
import jdbiexample.model.auction.IAuctionRepository;
import jdbiexample.model.bidHistory.IBidHistoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static zeliba.the.TheComparable.the;

public class AuctionStatusQuery {

    private final IAuctionRepository auctionRepository;
    private final IBidHistoryRepository bidHistoryRepository;
    private final Clock clock;

    public AuctionStatusQuery(IAuctionRepository auctionRepository, IBidHistoryRepository bidHistoryRepository, Clock clock) {
        this.auctionRepository = auctionRepository;
        this.bidHistoryRepository = bidHistoryRepository;
        this.clock = clock;
    }

    public AuctionStatus auctionStatus(UUID auctionId) {

        var auction = auctionRepository.findBy(auctionId);

        var snapshot = auction.getSnapshot();

        return convertToStatus(snapshot);
    }

    private AuctionStatus convertToStatus(AuctionSnapshot snapshot) {
        var status = new AuctionStatus();

        status.auctionEnds = snapshot.endsAt;
        status.id = snapshot.id;
        status.timeRemaining = timeRemaining(snapshot.endsAt);

        if (snapshot.winningBid != null) {
            status.numberOfBids = bidHistoryRepository.noOfBidsFor(snapshot.id);
            status.winningBidderId = snapshot.winningBid.biddersId;
            status.currentPrice = snapshot.winningBid.currentPrice;
        }

        return status;
    }

    public Duration timeRemaining(LocalDateTime auctionEnds) {
        if (the(clock.time()).isLessThan(auctionEnds))
            return Duration.between(auctionEnds, clock.time());
        else
            return Duration.ZERO;
    }
}
