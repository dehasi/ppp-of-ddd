package hibernateexample.model.auction;

import java.util.UUID;

public interface IAuctionRepository {

    void add(Auction item);
    Auction findBy(UUID id);
}
