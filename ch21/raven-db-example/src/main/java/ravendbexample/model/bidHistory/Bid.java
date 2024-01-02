package ravendbexample.model.bidHistory;


import ravendbexample.infrastructure.ValueObject;
import ravendbexample.model.auction.Money;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class Bid extends ValueObject<Bid> {

    private String id; // for Hibernate
    private String auctionId;
    private String bidderId;
    private Money amountBid;
    private LocalDateTime timeOfBid;

    public Bid(String auctionId, String bidderId, Money amountBid, LocalDateTime timeOfBid) {
        this.auctionId = requireNonNull(auctionId);
        this.bidderId = requireNonNull(bidderId);
        this.amountBid = requireNonNull(amountBid);
        this.timeOfBid = requireNonNull(timeOfBid);
    }

    public String auctionId() {
        return auctionId;
    }

    public String bidderId() {
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

    public String getId() {
        return id;
    }

    public Bid setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Bid setAuctionId(String auctionId) {
        this.auctionId = auctionId;
        return this;
    }

    public String getBidderId() {
        return bidderId;
    }

    public Bid setBidderId(String bidderId) {
        this.bidderId = bidderId;
        return this;
    }

    public Money getAmountBid() {
        return amountBid;
    }

    public Bid setAmountBid(Money amountBid) {
        this.amountBid = amountBid;
        return this;
    }

    public LocalDateTime getTimeOfBid() {
        return timeOfBid;
    }

    public Bid setTimeOfBid(LocalDateTime timeOfBid) {
        this.timeOfBid = timeOfBid;
        return this;
    }
}
