package ravendbexample.infrastructure;

import net.ravendb.client.documents.session.IDocumentSession;
import ravendbexample.model.auction.Auction;
import ravendbexample.model.auction.IAuctionRepository;

public class AuctionRepository implements IAuctionRepository {

    private final IDocumentSession documentSession;

    public AuctionRepository(IDocumentSession documentSession) {this.documentSession = documentSession;}

    @Override
    public void add(Auction auction) {
        documentSession.store(auction);
    }

    @Override
    public Auction findBy(String id) {
        return documentSession.load(Auction.class, id);
    }
}
