package jdbiexample.model.auction;

import java.util.UUID;

public record OutBid(UUID auctionId, UUID bidderId) {}
