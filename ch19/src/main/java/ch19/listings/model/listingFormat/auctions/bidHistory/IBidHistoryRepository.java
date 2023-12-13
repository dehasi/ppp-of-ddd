package ch19.listings.model.listingFormat.auctions.bidHistory;

import java.util.UUID;

public interface IBidHistoryRepository {

    int noOfBidsFor(UUID autionId);
    void add(Bid bid);
}
