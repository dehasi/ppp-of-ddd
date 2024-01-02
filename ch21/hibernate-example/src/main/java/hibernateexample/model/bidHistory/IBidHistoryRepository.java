package hibernateexample.model.bidHistory;

import java.util.UUID;

public interface IBidHistoryRepository {

    int noOfBidsFor(UUID auctionId);

    void add(Bid bid);
}
