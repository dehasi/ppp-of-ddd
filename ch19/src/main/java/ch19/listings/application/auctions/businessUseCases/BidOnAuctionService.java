package ch19.listings.application.auctions.businessUseCases;

import ch19.listings.infrastructure.Clock;
import ch19.listings.infrastructure.DomainEvents;
import ch19.listings.model.Money;
import ch19.listings.model.listingFormat.auctions.AuctionRepository;
import ch19.listings.model.listingFormat.auctions.BidPlaced;
import ch19.listings.model.listingFormat.auctions.Offer;
import ch19.listings.model.listingFormat.auctions.OutBid;
import ch19.listings.model.listingFormat.auctions.bidHistory.Bid;
import ch19.listings.model.listingFormat.auctions.bidHistory.IBidHistoryRepository;
import ch19.listings.model.members.MemberService;

import java.util.UUID;
import java.util.function.Consumer;

class BidOnAuctionService {

    private AuctionRepository auctions;
    private IBidHistoryRepository bidHistory;
    //private IDocumentSession unitOfWork;
    private Clock clock;
    private MemberService memberService;

    public BidOnAuctionService(AuctionRepository auctions, IBidHistoryRepository bidHistory, Clock clock, MemberService memberService) {
        this.auctions = auctions;
        this.bidHistory = bidHistory;
        this.clock = clock;
        this.memberService = memberService;
    }

    public void bid(UUID auctionId, UUID memberId, double amount) {
        DomainEvents.register(outBid());
        DomainEvents.register(bidPlaced());

        var member = memberService.getMember(memberId);

        if (member.canBid()) {
            var auction = auctions.findBy(auctionId);

            var bidAmount = new Money(amount);

            var offer = new Offer(memberId, bidAmount, clock.time());

            auction.placeBidFor(offer, clock.time());
        }
    }

    private Consumer<BidPlaced> bidPlaced() {
        return (BidPlaced e) -> {
            var bidEvent = new Bid(UUID.randomUUID(), e.auctionId(), e.bidderId(), e.amountBid(), e.timeOfBid());

            bidHistory.add(bidEvent);
        };
    }

    private Consumer<OutBid> outBid() {
        return (OutBid e) -> {
            // Add message to Member message board.
            //
        };
    }
}
