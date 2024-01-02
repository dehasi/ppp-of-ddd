package hibernateexample.model.auction;

import java.time.LocalDateTime;
import java.util.UUID;

public class WinningBid {

    UUID bidder;
    Money maximumBid;
    Money bid;
    LocalDateTime timeOfBid;
    Price currentAuctionPrice;

    public WinningBid() {
        // for Hibernate
    }

    public WinningBid(UUID bidder, Money maximumBid, Money bid, LocalDateTime timeOfBid) {

        this.bidder = bidder;
        this.maximumBid = maximumBid;
        this.bid = bid;
        this.timeOfBid = timeOfBid;
        currentAuctionPrice = new Price(this.bid);
    }

    public WinningBid raiseMaximumBidTo(Money newAmount) {
        if (newAmount.IsGreaterThan(maximumBid))
            return new WinningBid(bidder, newAmount, currentAuctionPrice.amount(), LocalDateTime.now());
        else
            throw new RuntimeException("Maximum bid increase must be larger than current maximum bid.");
    }

    public boolean wasMadeBy(UUID bidder) {
        return this.bidder.equals(bidder);
    }

    public boolean canBeExceededBy(Money offer) {
        return currentAuctionPrice.canBeExceededBy(offer);
    }

    public boolean hasNotReachedMaximumBid() {
        return maximumBid.IsGreaterThan(currentAuctionPrice.amount());
    }
}
