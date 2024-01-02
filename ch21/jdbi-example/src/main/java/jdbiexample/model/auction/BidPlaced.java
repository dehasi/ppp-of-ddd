package jdbiexample.model.auction;

import java.time.LocalDateTime;
import java.util.UUID;

public record BidPlaced(
        UUID auctionId,
        UUID bidderId,
        Money amountBid,
        LocalDateTime timeOfBid
) {}
