package jdbiexample.infrastructure.datamodel;

import jdbiexample.infrastructure.IAggregateDataModel;

import java.time.LocalDateTime;


// experiment with fields and records
public class AuctionDTO implements IAggregateDataModel {

    public String id;
    public int version;
    public double startingPrice;
    public LocalDateTime auctionEnds;

    // nullable
    public String bidderMemberId;
    public LocalDateTime timeOfBid;
    public Double maximumBid;
    public Double currentPrice;

}
