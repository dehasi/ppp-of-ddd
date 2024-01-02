package hibernateexample;

import hibernateexample.application.businessUseCases.BidOnAuction;
import hibernateexample.application.businessUseCases.CreateAuction;
import hibernateexample.application.queries.AuctionStatusQuery;
import hibernateexample.application.queries.BidHistoryQuery;
import hibernateexample.infrastructure.AuctionRepository;
import hibernateexample.infrastructure.BidHistoryRepository;
import hibernateexample.infrastructure.SystemClock;
import hibernateexample.model.auction.IAuctionRepository;
import hibernateexample.model.bidHistory.IBidHistoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup() {
        Map<Class<?>, Object> beans = new HashMap<>();

        Session session = createSession();
        SystemClock clock = new SystemClock();
        IAuctionRepository auctionRepository = new AuctionRepository(session);
        IBidHistoryRepository bidHistoryRepository = new BidHistoryRepository(session);

        CreateAuction createAuction = new CreateAuction(auctionRepository, session);
        BidOnAuction bidOnAuction = new BidOnAuction(auctionRepository, bidHistoryRepository, session, clock);
        AuctionStatusQuery auctionStatusQuery = new AuctionStatusQuery(session, bidHistoryRepository, clock);
        BidHistoryQuery bidHistoryQuery = new BidHistoryQuery(session);

        beans.put(createAuction.getClass(), createAuction);
        beans.put(bidOnAuction.getClass(), bidOnAuction);
        beans.put(auctionStatusQuery.getClass(), auctionStatusQuery);
        beans.put(bidHistoryQuery.getClass(), bidHistoryQuery);

        return new ObjectFactory(beans);
    }

    private static Session createSession() {
        Configuration configuration = new Configuration();

        SessionFactory sessionFactory = configuration
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        return sessionFactory.openSession();
    }

    public static class ObjectFactory {
        private final Map<Class<?>, Object> beans;

        private ObjectFactory(Map<Class<?>, Object> beans) {this.beans = beans;}

        public <T> T getInstance(Class<T> clazz) {
            return (T) beans.get(clazz);
        }
    }
}
