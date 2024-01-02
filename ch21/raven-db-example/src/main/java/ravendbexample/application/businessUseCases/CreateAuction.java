package ravendbexample.application.businessUseCases;

import net.ravendb.client.documents.session.IDocumentSession;
import ravendbexample.model.auction.Auction;
import ravendbexample.model.auction.IAuctionRepository;
import ravendbexample.model.auction.Money;

import java.util.UUID;

public class CreateAuction {

    private final IAuctionRepository auctionRepository;
    private final IDocumentSession unitOfWork;

    public CreateAuction(IAuctionRepository auctionRepository, IDocumentSession unitOfWork) {
        this.auctionRepository = auctionRepository;
        this.unitOfWork = unitOfWork;
    }

    public String create(NewAuctionRequest command) {
        var auctionId = UUID.randomUUID().toString();
        var startingPrice = new Money(command.startingPrice());

        auctionRepository.add(new Auction(auctionId, startingPrice, command.endsAt()));

        unitOfWork.saveChanges();
        return auctionId;
    }
}
