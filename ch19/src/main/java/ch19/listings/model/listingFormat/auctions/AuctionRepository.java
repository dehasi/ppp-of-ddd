package ch19.listings.model.listingFormat.auctions;

import java.util.UUID;

public interface AuctionRepository {

    void add(Auction item);
    Auction findBy(UUID Id);
}
