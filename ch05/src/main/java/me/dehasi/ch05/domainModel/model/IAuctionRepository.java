package me.dehasi.ch05.domainModel.model;

import java.util.UUID;

public interface IAuctionRepository {

    void add(Auction item);

    Auction findBy(UUID Id);
}
