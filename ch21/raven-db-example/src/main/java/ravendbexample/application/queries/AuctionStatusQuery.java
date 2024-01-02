package ravendbexample.application.queries;

import ravendbexample.infrastructure.Clock;
import ravendbexample.model.auction.IAuctionRepository;
import ravendbexample.model.bidHistory.IBidHistoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public AuctionStatus auctionStatus(String auctionId) {
        var auction = auctionRepository.findBy(auctionId);
        var status = new AuctionStatus();

        status.auctionEnds = auction.endsAt;
        status.id = auction.id;

        if (auction.hasBeenBidOn()) {
            status.currentPrice = auction.winningBid.currentAuctionPrice.amount().value();
            status.winningBidderId = auction.winningBid.bidder;
        }

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
