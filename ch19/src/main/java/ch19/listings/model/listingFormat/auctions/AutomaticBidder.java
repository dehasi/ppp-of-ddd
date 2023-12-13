package ch19.listings.model.listingFormat.auctions;

import java.util.ArrayList;
import java.util.List;

class AutomaticBidder {

    public List<WinningBid> GenerateNextSequenceOfBidsAfter(Offer offer, WinningBid currentWinningBid) {
        var bids = new ArrayList<WinningBid>();

        if (currentWinningBid.maximumBid.isGreaterThanOrEqualTo(offer.maximumBid())) {
            var bidFromOffer = new WinningBid(offer.bidder(), offer.maximumBid(), offer.maximumBid(), offer.timeOfOffer());
            bids.add(bidFromOffer);

            bids.add(CalculateNextBid(bidFromOffer, new Offer(currentWinningBid.bidder, currentWinningBid.maximumBid, currentWinningBid.timeOfBid)));
        } else {
            if (currentWinningBid.hasNotReachedMaximumBid()) {
                var currentBiddersLastBid = new WinningBid(currentWinningBid.bidder, currentWinningBid.maximumBid, currentWinningBid.maximumBid, currentWinningBid.timeOfBid);
                bids.add(currentBiddersLastBid);

                bids.add(CalculateNextBid(currentBiddersLastBid, offer));
            } else
                bids.add(new WinningBid(offer.bidder(), currentWinningBid.currentAuctionPrice.bidIncrement(), offer.maximumBid(), offer.timeOfOffer()));
        }

        return bids;
    }

    private WinningBid CalculateNextBid(WinningBid winningbid, Offer offer) {
        WinningBid bid;

        if (winningbid.canBeExceededBy(offer.maximumBid()))
            bid = new WinningBid(offer.bidder(), offer.maximumBid(), winningbid.currentAuctionPrice.bidIncrement(), offer.timeOfOffer());
        else
            bid = new WinningBid(offer.bidder(), offer.maximumBid(), offer.maximumBid(), offer.timeOfOffer());

        return bid;
    }
}
