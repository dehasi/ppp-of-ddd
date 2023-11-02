package me.dehasi.ch05.domainModel.model;

import java.time.LocalDateTime;
import java.util.UUID;

class HistoricalBid {

    UUID bidder;
    Money amount;
    LocalDateTime timeOfBid;

    HistoricalBid(UUID bidder, Money bid, LocalDateTime timeOfBid) {
        this.bidder = bidder;
        this.amount = bid;
        this.timeOfBid = timeOfBid;
    }
}
