package ravendbexample.model.auction;

import java.time.LocalDateTime;

public record BidPlaced(
        String auctionId,
        String bidderId,
        Money amountBid,
        LocalDateTime timeOfBid
) {}
