package hibernateexample.application.queries;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuctionStatus {

    public UUID id;
    public double currentPrice;
    public LocalDateTime auctionEnds;
    public UUID winningBidderId;
    public int numberOfBids;
    public Duration timeRemaining;

    public AuctionStatus(String ID, double CURRENTPRICE, String WINNINGBIDDERID, Timestamp AUCTIONENDS) {
        this.id = UUID.fromString(ID);
        this.currentPrice = CURRENTPRICE;
        this.auctionEnds = AUCTIONENDS.toLocalDateTime();
        this.winningBidderId = UUID.fromString(WINNINGBIDDERID);
    }

    public UUID getId() {
        return id;
    }

    public AuctionStatus setId(UUID id) {
        this.id = id;
        return this;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public AuctionStatus setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public LocalDateTime getAuctionEnds() {
        return auctionEnds;
    }

    public AuctionStatus setAuctionEnds(LocalDateTime auctionEnds) {
        this.auctionEnds = auctionEnds;
        return this;
    }

    public UUID getWinningBidderId() {
        return winningBidderId;
    }

    public AuctionStatus setWinningBidderId(UUID winningBidderId) {
        this.winningBidderId = winningBidderId;
        return this;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public AuctionStatus setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
        return this;
    }

    public Duration getTimeRemaining() {
        return timeRemaining;
    }

    public AuctionStatus setTimeRemaining(Duration timeRemaining) {
        this.timeRemaining = timeRemaining;
        return this;
    }
}
