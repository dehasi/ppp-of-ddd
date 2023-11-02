package me.dehasi.ch05.domainModel.application;

import me.dehasi.ch05.domainModel.model.Bid;
import me.dehasi.ch05.domainModel.model.IAuctionRepository;
import me.dehasi.ch05.domainModel.model.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class BidOnAuctionService {

    private final IAuctionRepository auctions;

    BidOnAuctionService(IAuctionRepository auctions) {
        this.auctions = auctions;
    }

    public void Bid(UUID auctionId, UUID memberId, BigDecimal amount, LocalDateTime dateOfBid) {
        var auction = auctions.findBy(auctionId);
        var bidAmount = new Money(amount);
        var offer = new Bid(memberId, bidAmount, dateOfBid);

        auction.placeBidFor(offer, dateOfBid);
    }
}
