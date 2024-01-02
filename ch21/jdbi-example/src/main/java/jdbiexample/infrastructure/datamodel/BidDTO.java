package jdbiexample.infrastructure.datamodel;

import jdbiexample.infrastructure.IAggregateDataModel;

import java.time.LocalDateTime;

public class BidDTO implements IAggregateDataModel {

    public String id;
    public String auctionId;
    public String bidderId;
    public double bid;
    public LocalDateTime timeOfBid;
}
