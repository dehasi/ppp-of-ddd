package jdbiexample.model.auction;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuctionSnapshot {

    public UUID id;
    public double startingPrice;
    public LocalDateTime endsAt;
    public WinningBidSnapshot winningBid;
    public int version;
}
