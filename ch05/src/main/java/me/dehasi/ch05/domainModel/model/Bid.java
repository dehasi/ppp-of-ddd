package me.dehasi.ch05.domainModel.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Bid {

    UUID bidder;
    Money maximumBid;
    LocalDateTime timeOfOffer;

    public Bid(UUID bidderId, Money maximumBid, LocalDateTime timeOfOffer) {
        this.bidder = requireNonNull(bidderId, "bidderId cannot be null");
        this.maximumBid = requireNonNull(maximumBid, "maximumBid cannot be null");
        this.timeOfOffer = requireNonNull(timeOfOffer, "time of offer must have a value");
        if (timeOfOffer == LocalDateTime.MIN)
            throw new IllegalArgumentException("Time of Offer must have a value");
    }
}
