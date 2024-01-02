package jdbiexample.model.auction;

import java.time.LocalDateTime;
import java.util.UUID;

public class WinningBidSnapshot {

    public UUID biddersId;
    public LocalDateTime timeOfBid;
    public double biddersMaximumBid;
    public double currentPrice;
}
