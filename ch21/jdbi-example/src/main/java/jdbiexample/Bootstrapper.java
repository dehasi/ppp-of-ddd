package jdbiexample;

import jdbiexample.application.businessUseCases.BidOnAuction;
import jdbiexample.application.businessUseCases.CreateAuction;
import jdbiexample.application.queries.AuctionStatusQuery;
import jdbiexample.application.queries.BidHistoryQuery;
import jdbiexample.infrastructure.System;
import jdbiexample.infrastructure.*;
import jdbiexample.infrastructure.datamodel.AuctionDTO;
import jdbiexample.infrastructure.datamodel.BidDTO;
import jdbiexample.infrastructure.mapper.AuctionDTOMapper;
import jdbiexample.infrastructure.mapper.BidDTOMapper;
import jdbiexample.model.auction.IAuctionRepository;
import jdbiexample.model.bidHistory.IBidHistoryRepository;
import org.jdbi.v3.core.Jdbi;

import java.util.HashMap;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup(String url) {
        Map<Class<?>, Object> beans = new HashMap<>();

        Jdbi jdbi = createJdbi(url);
        System system = new System(jdbi);
        IUnitOfWork unitOfWork = new UnitOfWork(system);
        SystemClock clock = new SystemClock();
        IAuctionRepository auctionRepository = new AuctionRepository(unitOfWork, system);
        IBidHistoryRepository bidHistoryRepository = new BidHistoryRepository(unitOfWork, system);

        CreateAuction createAuction = new CreateAuction(auctionRepository, unitOfWork);
        BidOnAuction bidOnAuction = new BidOnAuction(auctionRepository, bidHistoryRepository, unitOfWork, clock);
        AuctionStatusQuery auctionStatusQuery = new AuctionStatusQuery(auctionRepository, bidHistoryRepository, clock);
        BidHistoryQuery bidHistoryQuery = new BidHistoryQuery(bidHistoryRepository);

        beans.put(createAuction.getClass(), createAuction);
        beans.put(bidOnAuction.getClass(), bidOnAuction);
        beans.put(auctionStatusQuery.getClass(), auctionStatusQuery);
        beans.put(bidHistoryQuery.getClass(), bidHistoryQuery);

        return new ObjectFactory(beans);
    }

    private static Jdbi createJdbi(String connectionURL) {
        var jdbi = Jdbi.create(connectionURL);
        jdbi.registerRowMapper(AuctionDTO.class, new AuctionDTOMapper());
        jdbi.registerRowMapper(BidDTO.class, new BidDTOMapper());
        return jdbi;
    }

    public static class ObjectFactory {
        private final Map<Class<?>, Object> beans;

        private ObjectFactory(Map<Class<?>, Object> beans) {this.beans = beans;}

        public <T> T getInstance(Class<T> clazz) {
            return (T) beans.get(clazz);
        }
    }
}
