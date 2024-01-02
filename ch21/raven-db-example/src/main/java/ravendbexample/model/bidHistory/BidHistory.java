package ravendbexample.model.bidHistory;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

public record BidHistory(List<Bid> bids) {

    public BidHistory(List<Bid> bids) {
        this.bids = requireNonNull(bids).stream()
                .sorted(comparing(Bid::amountBid).thenComparing(Bid::timeOfBid))
                .toList();
    }
}
