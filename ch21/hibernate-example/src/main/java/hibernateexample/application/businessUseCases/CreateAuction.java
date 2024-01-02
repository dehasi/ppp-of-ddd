package hibernateexample.application.businessUseCases;

import hibernateexample.model.auction.Auction;
import hibernateexample.model.auction.IAuctionRepository;
import hibernateexample.model.auction.Money;
import org.hibernate.Session;

import java.util.UUID;

public class CreateAuction {

    private final IAuctionRepository auctionRepository;
    private final Session unitOfWork;

    public CreateAuction(IAuctionRepository auctionRepository, Session unitOfWork) {
        this.auctionRepository = auctionRepository;
        this.unitOfWork = unitOfWork;
    }

    public UUID create(NewAuctionRequest command) {
        var auctionId = UUID.randomUUID();
        var startingPrice = new Money(command.startingPrice());

        var transaction = unitOfWork.beginTransaction();

        auctionRepository.add(new Auction(auctionId, startingPrice, command.endsAt()));
        transaction.commit();

        return auctionId;
    }
}
