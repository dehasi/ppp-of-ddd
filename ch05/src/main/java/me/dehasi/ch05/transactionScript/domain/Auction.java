package me.dehasi.ch05.transactionScript.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class Auction {

    UUID id;
    UUID listingId;
    LocalDateTime endsAt;
    BigDecimal startingPrice;
    BigDecimal winningBid;
    BigDecimal winningBidderMaximumBid;
    UUID winningBidder;
}
