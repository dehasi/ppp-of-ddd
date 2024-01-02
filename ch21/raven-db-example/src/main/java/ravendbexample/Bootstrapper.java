package ravendbexample;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.indexes.IndexCreation;
import net.ravendb.client.documents.session.IDocumentSession;
import ravendbexample.application.businessUseCases.BidOnAuction;
import ravendbexample.application.businessUseCases.CreateAuction;
import ravendbexample.application.queries.AuctionStatusQuery;
import ravendbexample.application.queries.BidHistoryQuery;
import ravendbexample.infrastructure.AuctionRepository;
import ravendbexample.infrastructure.BidHistoryRepository;
import ravendbexample.infrastructure.BidHistory_NumberOfBids;
import ravendbexample.infrastructure.SystemClock;
import ravendbexample.model.auction.IAuctionRepository;
import ravendbexample.model.bidHistory.IBidHistoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup() {
        Map<Class<?>, Object> beans = new HashMap<>();

        IDocumentSession session = createSession();
        SystemClock clock = new SystemClock();
        IAuctionRepository auctionRepository = new AuctionRepository(session);
        IBidHistoryRepository bidHistoryRepository = new BidHistoryRepository(session);

        CreateAuction createAuction = new CreateAuction(auctionRepository, session);
        BidOnAuction bidOnAuction = new BidOnAuction(auctionRepository, bidHistoryRepository, session, clock);
        AuctionStatusQuery auctionStatusQuery = new AuctionStatusQuery(auctionRepository, bidHistoryRepository, clock);
        BidHistoryQuery bidHistoryQuery = new BidHistoryQuery(bidHistoryRepository);

        beans.put(createAuction.getClass(), createAuction);
        beans.put(bidOnAuction.getClass(), bidOnAuction);
        beans.put(auctionStatusQuery.getClass(), auctionStatusQuery);
        beans.put(bidHistoryQuery.getClass(), bidHistoryQuery);

        return new ObjectFactory(beans);
    }

    private static IDocumentSession createSession() {
        var documentStore = new DocumentStore("http://localhost:8080", "ch21");
        documentStore.initialize();
        documentStore.getConventions().getEntityMapper().registerModule(new JavaTimeModule());

        IDocumentSession session = documentStore.openSession();
        session.advanced().setUseOptimisticConcurrency(true);

//        BidHistory_NumberOfBids index = new BidHistory_NumberOfBids();
//        index.execute(documentStore);
        IndexCreation.createIndexes(List.of(new BidHistory_NumberOfBids()), documentStore);
        return session;
    }

    public static class ObjectFactory {
        private final Map<Class<?>, Object> beans;

        private ObjectFactory(Map<Class<?>, Object> beans) {this.beans = beans;}

        public <T> T getInstance(Class<T> clazz) {
            return (T) beans.get(clazz);
        }
    }
}
