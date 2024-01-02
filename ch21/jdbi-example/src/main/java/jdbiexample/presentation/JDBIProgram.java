package jdbiexample.presentation;

import jdbiexample.Bootstrapper;
import jdbiexample.Bootstrapper.ObjectFactory;
import jdbiexample.application.businessUseCases.BidOnAuction;
import jdbiexample.application.businessUseCases.CreateAuction;
import jdbiexample.application.businessUseCases.NewAuctionRequest;
import jdbiexample.application.queries.AuctionStatusQuery;
import jdbiexample.application.queries.BidHistoryQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class JDBIProgram {

    private static final Map<UUID, String> members = new HashMap<>();
    private static ObjectFactory objectFactory;

    public static void main(String[] args) {
        // "jdbc:sqlite:/Users/ravil/projects/ppp-of-ddd/ch21/sqlite-example/src/main/resources/ch21.db"
        objectFactory = Bootstrapper.startup(args[0]);

        var memberIdA = UUID.randomUUID();
        var memberIdB = UUID.randomUUID();

        members.put(memberIdA, "Ted");
        members.put(memberIdB, "Rob");

        var auctionId = createAuction();

        bid(auctionId, memberIdA, 10);
        bid(auctionId, memberIdB, 1.49);
        bid(auctionId, memberIdB, 10.01);
        bid(auctionId, memberIdB, 12.00);
        bid(auctionId, memberIdA, 13.00);
    }

    static UUID createAuction() {
        var createAuctionService = objectFactory.getInstance(CreateAuction.class);

        var newAuctionRequest = new NewAuctionRequest(0.99, LocalDateTime.now().plusDays(1));

        var auctionId = createAuctionService.create(newAuctionRequest);

        return auctionId;
    }

    static void bid(UUID auctionId, UUID memberId, double amount) {
        var bidOnAuctionService = objectFactory.getInstance(BidOnAuction.class);

        bidOnAuctionService.bid(auctionId, memberId, amount);

        printStatusOfAuctionBy(auctionId);
        printBidHistoryOf(auctionId);
        System.out.println("Hit any key to continue");

        readLine();
    }

    private static void printStatusOfAuctionBy(UUID auctionId) {
        var auctionSummaryQuery = objectFactory.getInstance(AuctionStatusQuery.class);
        var status = auctionSummaryQuery.auctionStatus(auctionId);

        System.out.println("No Of Bids: " + status.numberOfBids);
        System.out.println("Current Bid: " + status.currentPrice); //.ToString("##.##")
        System.out.println("Winning Bidder: " + findNameOfBidderWith(status.winningBidderId));
        System.out.println("Time Remaining: " + status.timeRemaining);
        System.out.println();
    }

    private static void printBidHistoryOf(UUID auctionId) {
        var bidHistoryQuery = objectFactory.getInstance(BidHistoryQuery.class);
        var status = bidHistoryQuery.bidHistoryFor(auctionId);

        System.out.println("Bids..");

        for (var bid : status)
            System.out.println(findNameOfBidderWith(bid.bidder()) + "\t - " + bid.amountBid() + "\t at " + bid.timeOfBid());

        System.out.println("------------------------------");
        System.out.println();
    }

    private static String findNameOfBidderWith(UUID id) {
        return members.getOrDefault(id, "");
    }

    private static void readLine() {
        try {
            System.console().readLine();
        } catch (Exception ignore) {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e1) {
                throw new RuntimeException(e1);
            }
        }
    }
}
