package jdbiexample.model.auction;

import jdbiexample.infrastructure.DomainEvents;
import jdbiexample.infrastructure.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Auction extends Entity<UUID> {

    private LocalDateTime endsAt;
    private Money startingPrice;
    private WinningBid winningBid; // CurrentWinningBid

    public Auction(UUID id, Money startingPrice, LocalDateTime endsAt) {
        this.id = requireNonNull(id);
        this.startingPrice = requireNonNull(startingPrice);
        this.endsAt = requireNonNull(endsAt);
    }

    private Auction(AuctionSnapshot snapshot) {
        this.id = snapshot.id;
        this.startingPrice = new Money(snapshot.startingPrice);
        this.endsAt = snapshot.endsAt;
        this.version = snapshot.version;

        if (snapshot.winningBid != null)
            this.winningBid = WinningBid.createFrom(snapshot.winningBid);
    }

    public static Auction createFrom(AuctionSnapshot snapshot) {
        return new Auction(snapshot);
    }

    public AuctionSnapshot getSnapshot() {
        var snapshot = new AuctionSnapshot();
        snapshot.id = this.id;
        snapshot.startingPrice = this.startingPrice.getSnapshot().value;
        snapshot.endsAt = this.endsAt;
        snapshot.version = this.version;

        if (hasACurrentBid())
            snapshot.winningBid = winningBid.getSnapshot();

        return snapshot;
    }

    private boolean hasACurrentBid() {
        return winningBid != null;
    }

    public void reduceTheStartingPrice() {
        // Only if no bids and more than 12 hours left
    }

    // Fixed Price or
    // Auction
    // Once someone bids, the Buy it now option disappears.
    // The listing then proceeds as a regular auction-style listing, with the item going to the highest bidderId.
    // (If the auction has a reserve price, the Buy it now option will be available until the reserve price is met.)
    // http://sellercentre.ebay.co.uk/add-buy-it-now-price-auction

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
