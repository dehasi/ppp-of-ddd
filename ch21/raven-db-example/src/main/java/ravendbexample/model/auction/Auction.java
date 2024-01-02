package ravendbexample.model.auction;

import ravendbexample.infrastructure.DomainEvents;
import ravendbexample.infrastructure.Entity;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Auction extends Entity {

    public LocalDateTime endsAt;
    private Money startingPrice;
    public WinningBid winningBid;

    public Auction(String id, Money startingPrice, LocalDateTime endsAt) {
        this.id = requireNonNull(id).toString();
        this.startingPrice = requireNonNull(startingPrice);
        this.endsAt = requireNonNull(endsAt);
    }

    public boolean hasBeenBidOn() {
        return winningBid != null;
    }

    private boolean stillInProgress(LocalDateTime currentTime) {
        return endsAt.isAfter(currentTime);
    }

    public void placeBidFor(Offer offer, LocalDateTime currentTime) {
        if (stillInProgress(currentTime)) {
            if (firstOffer())
                placeABidForTheFirst(offer);
            else if (bidderIsIncreasingMaximumBidToNew(offer))
                winningBid = winningBid.raiseMaximumBidTo(offer.maximumBid());
            else if (winningBid.canBeExceededBy(offer.maximumBid())) {
                var newBids = new AutomaticBidder().generateNextSequenceOfBidsAfter(offer, winningBid);

                for (var bid : newBids)
                    place(bid);
            }
        }
    }

    private boolean bidderIsIncreasingMaximumBidToNew(Offer offer) {
        return winningBid.wasMadeBy(offer.bidder()) && offer.maximumBid().IsGreaterThan(winningBid.maximumBid);
    }

    private boolean firstOffer() {
        return winningBid == null;
    }

    private void placeABidForTheFirst(Offer offer) {
        if (offer.maximumBid().isGreaterThanOrEqualTo(startingPrice))
            place(new WinningBid(offer.bidder(), offer.maximumBid(), startingPrice, offer.timeOfOffer()));
    }

    private void place(WinningBid newBid) {
        if (!firstOffer() && winningBid.wasMadeBy(newBid.bidder))
            DomainEvents.raise(new OutBid(id, newBid.bidder));

        winningBid = newBid;
        DomainEvents.raise(new BidPlaced(id, newBid.bidder, newBid.currentAuctionPrice.amount(), newBid.timeOfBid));
    }
}
