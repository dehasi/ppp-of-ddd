package ravendbexample.application.queries;

import java.time.LocalDateTime;


public record BidInformation(
        String bidder,
        double amountBid,
        LocalDateTime timeOfBid
) {}
