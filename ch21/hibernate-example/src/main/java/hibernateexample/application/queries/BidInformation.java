package hibernateexample.application.queries;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class BidInformation {

    public UUID bidder;
    public double amountBid;
    public String currency;
    public LocalDateTime timeOfBid;

    public BidInformation(String BIDDER, double AMOUNTBID, Timestamp TIME_OF_BID) {
        this.bidder = UUID.fromString(BIDDER);
        this.amountBid = AMOUNTBID;
        this.timeOfBid = TIME_OF_BID.toLocalDateTime();
    }
}
