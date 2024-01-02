package jooqexample.application.queries;

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
}
