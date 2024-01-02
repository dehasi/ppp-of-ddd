package jdbiexample.model.auction;

import jdbiexample.infrastructure.ValueObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class WinningBid extends ValueObject<WinningBid> {

    UUID bidder;
    Money maximumBid;
    Money bid;
    LocalDateTime timeOfBid;
    Price currentAuctionPrice;

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

    public WinningBidSnapshot getSnapshot() {
        var snapshot = new WinningBidSnapshot();

        snapshot.biddersId = this.bidder;
        snapshot.biddersMaximumBid = this.maximumBid.getSnapshot().value;
        snapshot.currentPrice = this.currentAuctionPrice.amount().getSnapshot().value;
        snapshot.timeOfBid = this.timeOfBid;

        return snapshot;
    }

    public static WinningBid createFrom(WinningBidSnapshot bidSnapShot) {
        return new WinningBid(bidSnapShot.biddersId, new Money(bidSnapShot.biddersMaximumBid), new Money(bidSnapShot.currentPrice), bidSnapShot.timeOfBid);
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(bidder, maximumBid, timeOfBid, currentAuctionPrice);
    }
}
