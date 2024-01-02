package jooqexample;

import jooqexample.application.businessUseCases.BidOnAuction;
import jooqexample.application.businessUseCases.CreateAuction;
import jooqexample.application.queries.AuctionStatusQuery;
import jooqexample.application.queries.BidHistoryQuery;
import jooqexample.infrastructure.AuctionRepository;
import jooqexample.infrastructure.BidHistoryRepository;
import jooqexample.infrastructure.SystemClock;
import jooqexample.model.auction.IAuctionRepository;
import jooqexample.model.bidHistory.IBidHistoryRepository;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup(String url) {
        Map<Class<?>, Object> beans = new HashMap<>();

        DSLContext dslContext = createContext(url);
        SystemClock clock = new SystemClock();
        IAuctionRepository auctionRepository = new AuctionRepository(dslContext);
        IBidHistoryRepository bidHistoryRepository = new BidHistoryRepository(dslContext);

        CreateAuction createAuction = new CreateAuction(auctionRepository, dslContext);
        BidOnAuction bidOnAuction = new BidOnAuction(auctionRepository, bidHistoryRepository, dslContext, clock);
        AuctionStatusQuery auctionStatusQuery = new AuctionStatusQuery(auctionRepository, bidHistoryRepository, clock);
        BidHistoryQuery bidHistoryQuery = new BidHistoryQuery(bidHistoryRepository);

        beans.put(createAuction.getClass(), createAuction);
        beans.put(bidOnAuction.getClass(), bidOnAuction);
        beans.put(auctionStatusQuery.getClass(), auctionStatusQuery);
        beans.put(bidHistoryQuery.getClass(), bidHistoryQuery);

        return new ObjectFactory(beans);
    }

    public static class ObjectFactory {
        private final Map<Class<?>, Object> beans;

        private ObjectFactory(Map<Class<?>, Object> beans) {this.beans = beans;}

        public <T> T getInstance(Class<T> clazz) {
            return (T) beans.get(clazz);
        }
    }

    private static DSLContext createContext(String url) {

        try {
            Connection conn = DriverManager.getConnection(url);
            return DSL.using(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
