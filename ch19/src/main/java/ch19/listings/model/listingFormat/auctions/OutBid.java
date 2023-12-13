package ch19.listings.model.listingFormat.auctions;

import java.util.UUID;

public record OutBid(UUID auctionId, UUID bidderId) {}
