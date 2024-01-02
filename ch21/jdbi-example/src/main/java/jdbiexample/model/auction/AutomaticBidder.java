package jdbiexample.model.auction;

import java.util.ArrayList;
import java.util.List;

class AutomaticBidder {

    public List<WinningBid> generateNextSequenceOfBidsAfter(Offer offer, WinningBid currentWinningBid) {
        var bids = new ArrayList<WinningBid>();

        if (currentWinningBid.maximumBid.isGreaterThanOrEqualTo(offer.maximumBid())) {
            var bidFromOffer = new WinningBid(offer.bidder(), offer.maximumBid(), offer.maximumBid(), offer.timeOfOffer());
            bids.add(bidFromOffer);

            bids.add(calculateNextBid(bidFromOffer, new Offer(currentWinningBid.bidder, currentWinningBid.maximumBid, currentWinningBid.timeOfBid)));
        } else {
            if (currentWinningBid.hasNotReachedMaximumBid()) {
                var currentBiddersLastBid = new WinningBid(currentWinningBid.bidder, currentWinningBid.maximumBid, currentWinningBid.maximumBid, currentWinningBid.timeOfBid);
                bids.add(currentBiddersLastBid);

                bids.add(calculateNextBid(currentBiddersLastBid, offer));
            } else
                bids.add(new WinningBid(offer.bidder(), currentWinningBid.currentAuctionPrice.bidIncrement(), offer.maximumBid(), offer.timeOfOffer()));
        }

        return bids;
    }

    private WinningBid calculateNextBid(WinningBid winningbid, Offer offer) {
        WinningBid bid;

        if (winningbid.canBeExceededBy(offer.maximumBid()))
            bid = new WinningBid(offer.bidder(), offer.maximumBid(), winningbid.currentAuctionPrice.bidIncrement(), offer.timeOfOffer());
        else
            bid = new WinningBid(offer.bidder(), offer.maximumBid(), offer.maximumBid(), offer.timeOfOffer());

        return bid;
    }
}
