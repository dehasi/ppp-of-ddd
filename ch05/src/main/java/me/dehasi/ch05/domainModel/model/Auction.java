package me.dehasi.ch05.domainModel.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Auction {

    private UUID id;
    private List<HistoricalBid> bids;
    private UUID listingId;
    private LocalDateTime endsAt;
    private Money startingPrice;
    private WinningBid winningBid;
    private boolean hasEnded;

    public Auction(UUID id, UUID listingId, Money startingPrice, LocalDateTime endsAt) {
        this.id = requireNonNull(id, "id cannot be null");
        this.listingId = requireNonNull(listingId, "listingId cannot be null");
        this.startingPrice = requireNonNull(startingPrice, "startingPrice cannot be null");
        this.endsAt = requireNonNull(endsAt, "endsAt cannot be null");
    }

    private boolean stillInProgress(LocalDateTime currentTime) {
        return endsAt.isAfter(currentTime);
    }

    public boolean sanPlaceBid() {
        return hasEnded == false;
    }

    public void placeBidFor(Bid bid, LocalDateTime currentTime) {
        if (stillInProgress(currentTime)) {
            if (isFirstBid())
                registerFirst(bid);
            else if (bidderIsIncreasingMaximumBid(bid))
                winningBid = winningBid.raiseMaximumBidTo(bid.maximumBid);
            else if (winningBid.canMeetOrExceedBidIncrement(bid.maximumBid)) {
                place(winningBid.DetermineWinningBidIncrement(bid));
            }
        }
    }

    private boolean bidderIsIncreasingMaximumBid(Bid bid) {
        return winningBid.wasMadeBy(bid.bidder) && bid.maximumBid.isGreaterThan(winningBid.maximumBid);
    }

    private boolean isFirstBid() {
        return winningBid == null;
    }

    private void registerFirst(Bid bid) {
        if (isFirstBid() && bid.maximumBid.isGreaterThanOrEqualTo(startingPrice))
            place(new WinningBid(bid.bidder, bid.maximumBid, startingPrice, bid.timeOfOffer));
    }

    private void place(WinningBid newBid) {
        bids.add(new HistoricalBid(newBid.bidder, newBid.maximumBid, newBid.timeOfBid));
        winningBid = newBid;
    }
}
