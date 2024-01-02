package ravendbexample.model.auction;

import java.time.LocalDateTime;


public class WinningBid {

    public String bidder;
    Money maximumBid;
    Money bid;
    LocalDateTime timeOfBid;
    public Price currentAuctionPrice;

    public String getBidder() {
        return bidder;
    }

    public WinningBid setBidder(String bidder) {
        this.bidder = bidder;
        return this;
    }

    public Money getMaximumBid() {
        return maximumBid;
    }

    public WinningBid setMaximumBid(Money maximumBid) {
        this.maximumBid = maximumBid;
        return this;
    }

    public Money getBid() {
        return bid;
    }

    public WinningBid setBid(Money bid) {
        this.bid = bid;
        return this;
    }

    public LocalDateTime getTimeOfBid() {
        return timeOfBid;
    }

    public WinningBid setTimeOfBid(LocalDateTime timeOfBid) {
        this.timeOfBid = timeOfBid;
        return this;
    }

    public Price getCurrentAuctionPrice() {
        return currentAuctionPrice;
    }

    public WinningBid setCurrentAuctionPrice(Price currentAuctionPrice) {
        this.currentAuctionPrice = currentAuctionPrice;
        return this;
    }

    public WinningBid(String bidder, Money maximumBid, Money bid, LocalDateTime timeOfBid) {

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

    public boolean wasMadeBy(String bidder) {
        return this.bidder.equals(bidder);
    }

    public boolean canBeExceededBy(Money offer) {
        return currentAuctionPrice.canBeExceededBy(offer);
    }

    public boolean hasNotReachedMaximumBid() {
        return maximumBid.IsGreaterThan(currentAuctionPrice.amount());
    }
}
