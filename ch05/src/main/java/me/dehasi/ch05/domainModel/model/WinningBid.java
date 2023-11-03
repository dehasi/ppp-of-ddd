package me.dehasi.ch05.domainModel.model;

import me.dehasi.replacements.exception.ApplicationException;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

class WinningBid {

    UUID auctionId;
    UUID bidder;
    Money maximumBid;
    LocalDateTime timeOfBid;
    Price currentAuctionPrice;

    public WinningBid(UUID bidder, Money maximumBid, Money bid, LocalDateTime timeOfBid) {
        this.bidder = requireNonNull(bidder, "bidder cannot be null");
        this.maximumBid = requireNonNull(maximumBid, "maximumBid cannot be null");
        this.timeOfBid = requireNonNull(timeOfBid, "timeOfBid must have a value");

        this.currentAuctionPrice = new Price(bid);
    }

    public WinningBid raiseMaximumBidTo(Money newAmount) {
        if (newAmount.isGreaterThan(maximumBid))
            return new WinningBid(bidder, newAmount, currentAuctionPrice.amount, LocalDateTime.now());
        else
            throw new ApplicationException("Maximum bid increase must be larger than current maximum bid.");
    }

    public boolean wasMadeBy(UUID bidder) {
        return this.bidder.equals(bidder);
    }

    public WinningBid DetermineWinningBidIncrement(Bid newbid) {
        if (this.canMeetOrExceedBidIncrement(this.maximumBid) && this.canMeetOrExceedBidIncrement(newbid.maximumBid)) {
            return determineWinnerFromProxyBidding(this, newbid);
        } else if (this.canMeetOrExceedBidIncrement(newbid.maximumBid)) {
            return createNewBid(newbid.bidder, currentAuctionPrice.bidIncrement(), newbid.maximumBid, newbid.timeOfOffer);
        } else
            return this;
    }

    private WinningBid determineWinnerFromProxyBidding(WinningBid winningBid, Bid newbid) {
        WinningBid nextIncrement;

        if (winningBid.maxBidCanBeExceededBy(newbid.maximumBid)) {
            nextIncrement = createNewBid(this.bidder, this.maximumBid, this.maximumBid, this.timeOfBid);

            if (nextIncrement.canMeetOrExceedBidIncrement(newbid.maximumBid))
                return createNewBid(newbid.bidder, nextIncrement.currentAuctionPrice.bidIncrement(), newbid.maximumBid, newbid.timeOfOffer);
            else
                return createNewBid(newbid.bidder, newbid.maximumBid, newbid.maximumBid, newbid.timeOfOffer);
        } else {
            nextIncrement = createNewBid(newbid.bidder, newbid.maximumBid, newbid.maximumBid, newbid.timeOfOffer);

            if (nextIncrement.canMeetOrExceedBidIncrement(winningBid.maximumBid))
                return createNewBid(winningBid.bidder, nextIncrement.currentAuctionPrice.bidIncrement(), winningBid.maximumBid, winningBid.timeOfBid);
            else
                return createNewBid(winningBid.bidder, winningBid.maximumBid, winningBid.maximumBid, winningBid.timeOfBid);
        }
    }

    private WinningBid createNewBid(UUID bidder, Money bid, Money maxBid, LocalDateTime timeOfBid) {
        return new WinningBid(bidder, bid, maxBid, timeOfBid);
    }

    private boolean maxBidCanBeExceededBy(Money bid) {
        return !this.maximumBid.isGreaterThanOrEqualTo(bid);
    }

    public boolean canMeetOrExceedBidIncrement(Money offer) {
        return currentAuctionPrice.canBeExceededBy(offer);
    }

    public boolean hasNotReachedMaximumBid() {
        return maximumBid.isGreaterThan(currentAuctionPrice.amount);
    }
}
