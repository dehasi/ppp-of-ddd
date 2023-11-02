package me.dehasi.ch05.transactionScript.application;

import me.dehasi.ch05.transactionScript.domain.BidOnAuctionCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class BidOnAuctionService {

    private final BidOnAuctionCommand bidOnAuctionCommand;

    BidOnAuctionService(BidOnAuctionCommand bidOnAuctionCommand) {this.bidOnAuctionCommand = bidOnAuctionCommand;}

    public void bid(UUID auctionId, UUID memberId, BigDecimal amount, LocalDateTime dateOfBid) {
        // bidOnAuctionCommand.execute(auctionId, memberId, amount, dateOfBid);
        new BidOnAuctionCommand(auctionId, memberId, amount, dateOfBid).execute();
    }
}
