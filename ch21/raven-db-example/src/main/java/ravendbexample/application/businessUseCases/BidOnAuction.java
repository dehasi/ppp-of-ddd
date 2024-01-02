package ravendbexample.application.businessUseCases;

import net.ravendb.client.documents.session.IDocumentSession;
import ravendbexample.infrastructure.Clock;
import ravendbexample.infrastructure.DomainEvents;
import ravendbexample.model.auction.*;
import ravendbexample.model.bidHistory.Bid;
import ravendbexample.model.bidHistory.IBidHistoryRepository;

import java.util.function.Consumer;

public class BidOnAuction {

    private final IAuctionRepository auctionRepository;
    private final IBidHistoryRepository bidHistoryRepository;
    private final IDocumentSession unitOfWork;
    private final Clock clock;

    public BidOnAuction(IAuctionRepository auctionRepository, IBidHistoryRepository bidHistoryRepository, IDocumentSession unitOfWork, Clock clock) {
        this.auctionRepository = auctionRepository;
        this.bidHistoryRepository = bidHistoryRepository;
        this.unitOfWork = unitOfWork;
        this.clock = clock;
    }

    public void bid(String auctionId, String memberId, double amount) {
        try {
            try (var ignored1 = DomainEvents.register(OutBid.class, outBid());
                 var ignored2 = DomainEvents.register(BidPlaced.class, bidPlaced())) {

                var auction = auctionRepository.findBy(auctionId);

                var bidAmount = new Money(amount);

                auction.placeBidFor(new Offer(memberId, bidAmount, clock.time()), clock.time());
            }

            unitOfWork.saveChanges();

        } catch (Exception e) {
            unitOfWork.advanced().clear();
            throw new RuntimeException(e);
           // bid(auctionId, memberId, amount); // not sure if recursion is good
        }
    }

    private Consumer<BidPlaced> bidPlaced() {
        return (BidPlaced e) -> {
            var bidEvent = new Bid(e.auctionId(), e.bidderId(), e.amountBid(), e.timeOfBid());

            bidHistoryRepository.add(bidEvent);
        };
    }

    private Consumer<OutBid> outBid() {
        return (OutBid e) -> {
            // Email customer to say that he has been out bid
        };
    }
}
