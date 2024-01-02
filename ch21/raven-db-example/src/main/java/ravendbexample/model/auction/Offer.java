package ravendbexample.model.auction;

import java.time.LocalDateTime;


public record Offer(
        String bidder,
        Money maximumBid,
        LocalDateTime timeOfOffer
) {}
