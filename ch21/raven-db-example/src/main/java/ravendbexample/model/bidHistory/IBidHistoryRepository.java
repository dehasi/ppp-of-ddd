package ravendbexample.model.bidHistory;



public interface IBidHistoryRepository {

    int noOfBidsFor(String auctionId);

    void add(Bid bid);

    BidHistory findBy(String auctionId);
}
