package jooqexample.application.businessUseCases;

import jooqexample.model.auction.Auction;
import jooqexample.model.auction.IAuctionRepository;
import jooqexample.model.auction.Money;
import org.jooq.DSLContext;

import java.util.UUID;

public class CreateAuction {

    private final IAuctionRepository auctionRepository;
    private final DSLContext unitOfWork;

    public CreateAuction(IAuctionRepository auctionRepository, DSLContext unitOfWork) {
        this.auctionRepository = auctionRepository;
        this.unitOfWork = unitOfWork;
    }

    public UUID create(NewAuctionRequest command) {
        var auctionId = UUID.randomUUID();
        var startingPrice = new Money(command.startingPrice());

//        var transaction = unitOfWork.beginTransaction();

        auctionRepository.add(new Auction(auctionId, startingPrice, command.endsAt()));
//        transaction.commit();

        return auctionId;
    }
}
