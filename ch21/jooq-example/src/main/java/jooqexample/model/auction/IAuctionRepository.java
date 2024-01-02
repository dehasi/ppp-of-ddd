package jooqexample.model.auction;

import java.util.UUID;

public interface IAuctionRepository {

    void add(Auction auction);
    void save(Auction auction); // for update
    Auction findBy(UUID id);
}
