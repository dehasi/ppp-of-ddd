package jooqexample.model.bidHistory;


import jooqexample.infrastructure.ValueObject;
import jooqexample.model.auction.Money;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class Bid extends ValueObject<Bid> {

    private UUID id; // for Hibernate
    private UUID auctionId;
    private UUID bidderId;
    private Money amountBid;
    private LocalDateTime timeOfBid;

    public Bid(UUID auctionId, UUID bidderId, Money amountBid, LocalDateTime timeOfBid) {
        this.auctionId = requireNonNull(auctionId);
        this.bidderId = requireNonNull(bidderId);
        this.amountBid = requireNonNull(amountBid);
        this.timeOfBid = requireNonNull(timeOfBid);
    }

    public UUID auctionId() {
        return auctionId;
    }

    public UUID bidderId() {
        return bidderId;
    }

    public Money amountBid() {
        return amountBid;
    }

    public LocalDateTime timeOfBid() {
        return timeOfBid;
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(auctionId, bidderId, amountBid, timeOfBid);
    }
}
