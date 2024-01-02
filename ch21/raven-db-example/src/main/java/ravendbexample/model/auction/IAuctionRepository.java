package ravendbexample.model.auction;



public interface IAuctionRepository {

    void add(Auction item);

    Auction findBy(String id);
}
