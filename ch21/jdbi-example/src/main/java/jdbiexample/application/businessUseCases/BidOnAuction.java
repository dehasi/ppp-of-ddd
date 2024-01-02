package jdbiexample.application.businessUseCases;

import jdbiexample.infrastructure.Clock;
import jdbiexample.infrastructure.ConcurrencyException;
import jdbiexample.infrastructure.DomainEvents;
import jdbiexample.infrastructure.IUnitOfWork;
import jdbiexample.model.auction.*;
import jdbiexample.model.bidHistory.Bid;
import jdbiexample.model.bidHistory.IBidHistoryRepository;

import java.util.UUID;
import java.util.function.Consumer;

public class BidOnAuction {

    private final IAuctionRepository auctionRepository;
    private final IBidHistoryRepository bidHistoryRepository;
    private final IUnitOfWork unitOfWork;
    private final Clock clock;

    public BidOnAuction(IAuctionRepository auctionRepository, IBidHistoryRepository bidHistoryRepository, IUnitOfWork unitOfWork, Clock clock) {
        this.auctionRepository = auctionRepository;
        this.bidHistoryRepository = bidHistoryRepository;
        this.unitOfWork = unitOfWork;
        this.clock = clock;
    }

    public void bid(UUID auctionId, UUID memberId, double amount) {
        try {
            // unitOfWork.startTransaction();
            try (var ignored1 = DomainEvents.register(OutBid.class, outBid());
                 var ignored2 = DomainEvents.register(BidPlaced.class, bidPlaced())) {

                var auction = auctionRepository.findBy(auctionId);

                var bidAmount = new Money(amount);

                auction.placeBidFor(new Offer(memberId, bidAmount, clock.time()), clock.time());
                auctionRepository.save(auction);
            }

            unitOfWork.commit();
        } catch (ConcurrencyException e) {
            unitOfWork.clear();
            throw new RuntimeException(e);
            // bid(auctionId, memberId, amount); // recursion here is not good
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
