package ch19.listings.model.listingFormat.auctions.bidHistory;

import ch19.listings.model.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public record Bid(
        UUID id,
        UUID auctionId,
        UUID bidder,
        Money amountBid,
        LocalDateTime timeOfBid
) {}
