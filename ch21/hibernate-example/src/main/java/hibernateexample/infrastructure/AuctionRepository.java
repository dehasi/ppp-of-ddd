package hibernateexample.infrastructure;

import hibernateexample.model.auction.Auction;
import hibernateexample.model.auction.IAuctionRepository;
import org.hibernate.Session;

import java.util.UUID;

public class AuctionRepository implements IAuctionRepository {

    private final Session session;

    public AuctionRepository(Session session) {this.session = session;}

    public void add(Auction auction) {
        session.save(auction);
    }

    public Auction findBy(UUID id) {
        return session.get(Auction.class, id);
    }
}
