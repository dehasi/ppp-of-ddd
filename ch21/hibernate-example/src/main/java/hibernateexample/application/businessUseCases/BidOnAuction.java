package hibernateexample.application.businessUseCases;

import hibernateexample.infrastructure.Clock;
import hibernateexample.infrastructure.DomainEvents;
import hibernateexample.model.auction.*;
import hibernateexample.model.bidHistory.Bid;
import hibernateexample.model.bidHistory.IBidHistoryRepository;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;

import java.util.UUID;
import java.util.function.Consumer;

public class BidOnAuction {

    private final IAuctionRepository auctionRepository;
    private final IBidHistoryRepository bidHistoryRepository;
    private final Session unitOfWork;
    private final Clock clock;

    public BidOnAuction(IAuctionRepository auctionRepository, IBidHistoryRepository bidHistoryRepository, Session unitOfWork, Clock clock) {
        this.auctionRepository = auctionRepository;
        this.bidHistoryRepository = bidHistoryRepository;
        this.unitOfWork = unitOfWork;
        this.clock = clock;
    }

    public void bid(UUID auctionId, UUID memberId, double amount) {
        try {
            Transaction transaction = unitOfWork.beginTransaction();
            {

                try (var ignored1 = DomainEvents.register(OutBid.class, outBid());
                     var ignored2 = DomainEvents.register(BidPlaced.class, bidPlaced())) {

                    var auction = auctionRepository.findBy(auctionId);

                    var bidAmount = new Money(amount);

                    auction.placeBidFor(new Offer(memberId, bidAmount, clock.time()), clock.time());
                }

                transaction.commit();
            }
        } catch (StaleObjectStateException ex) {
            unitOfWork.clear();

            bid(auctionId, memberId, amount); // not sure if recursion is good
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
