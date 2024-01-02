package jdbiexample.application.businessUseCases;

import jdbiexample.infrastructure.IUnitOfWork;
import jdbiexample.model.auction.Auction;
import jdbiexample.model.auction.IAuctionRepository;
import jdbiexample.model.auction.Money;

import java.util.UUID;

public class CreateAuction {

    private final IAuctionRepository auctionRepository;
    private final IUnitOfWork unitOfWork;

    public CreateAuction(IAuctionRepository auctionRepository, IUnitOfWork unitOfWork) {
        this.auctionRepository = auctionRepository;
        this.unitOfWork = unitOfWork;
    }

    public UUID create(NewAuctionRequest command) {
        var auctionId = UUID.randomUUID();
        var startingPrice = new Money(command.startingPrice());

        auctionRepository.add(new Auction(auctionId, startingPrice, command.endsAt()));

        unitOfWork.commit();
        return auctionId;
    }
}
