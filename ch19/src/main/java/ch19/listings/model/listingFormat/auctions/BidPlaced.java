package ch19.listings.model.listingFormat.auctions;

import ch19.listings.model.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public record BidPlaced(UUID auctionId, UUID bidderId, Money amountBid, LocalDateTime timeOfBid) {}
