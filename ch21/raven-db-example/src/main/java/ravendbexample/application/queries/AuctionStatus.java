package ravendbexample.application.queries;

import java.time.Duration;
import java.time.LocalDateTime;


public class AuctionStatus {

    public String id;
    public double currentPrice;
    public LocalDateTime auctionEnds;
    public String winningBidderId;
    public int numberOfBids;
    public Duration timeRemaining;

    public String getId() {
        return id;
    }

    public AuctionStatus setId(String id) {
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

    public String getWinningBidderId() {
        return winningBidderId;
    }

    public AuctionStatus setWinningBidderId(String winningBidderId) {
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
