package ravendbexample.presentation;

import ravendbexample.Bootstrapper;
import ravendbexample.Bootstrapper.ObjectFactory;
import ravendbexample.application.businessUseCases.BidOnAuction;
import ravendbexample.application.businessUseCases.CreateAuction;
import ravendbexample.application.businessUseCases.NewAuctionRequest;
import ravendbexample.application.queries.AuctionStatusQuery;
import ravendbexample.application.queries.BidHistoryQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


class ProgramRavenDB {

    private static final Map<String, String> members = new HashMap<>();
    private static ObjectFactory objectFactory;

    public static void main(String[] args) {
        objectFactory = Bootstrapper.startup();

        var memberIdA = UUID.randomUUID().toString();
        var memberIdB = UUID.randomUUID().toString();

        members.put(memberIdA, "Ted");
        members.put(memberIdB, "Rob");

        var auctionId = createAuction();

        bid(auctionId, memberIdA, 10);
        bid(auctionId, memberIdB, 1.49);
        bid(auctionId, memberIdB, 10.01);
        bid(auctionId, memberIdB, 12.00);
        bid(auctionId, memberIdA, 13.00);
    }

    static String createAuction() {
        var createAuctionService = objectFactory.getInstance(CreateAuction.class);

        var newAuctionRequest = new NewAuctionRequest(0.99, LocalDateTime.now().plusDays(1));

        var auctionId = createAuctionService.create(newAuctionRequest);

        return auctionId;
    }

    static void bid(String auctionId, String memberId, double amount) {
        var bidOnAuctionService = objectFactory.getInstance(BidOnAuction.class);

        bidOnAuctionService.bid(auctionId, memberId, amount);

        printStatusOfAuctionBy(auctionId);
        printBidHistoryOf(auctionId);
        System.out.println("Hit any key to continue");

        readLine();
    }

    private static void printStatusOfAuctionBy(String auctionId) {
        var auctionSummaryQuery = objectFactory.getInstance(AuctionStatusQuery.class);
        var status = auctionSummaryQuery.auctionStatus(auctionId);

        System.out.println("No Of Bids: " + status.numberOfBids);
        System.out.println("Current Bid: " + status.currentPrice); //.ToString("##.##")
        System.out.println("Winning Bidder: " + findNameOfBidderWith(status.winningBidderId));
        System.out.println("Time Remaining: " + status.timeRemaining);
        System.out.println();
    }

    private static void printBidHistoryOf(String auctionId) {
        var bidHistoryQuery = objectFactory.getInstance(BidHistoryQuery.class);
        var status = bidHistoryQuery.bidHistoryFor(auctionId);

        System.out.println("Bids..");

        for (var bid : status)
            System.out.println(findNameOfBidderWith(bid.bidder()) + "\t - " + bid.amountBid() + "\t at " + bid.timeOfBid());

        System.out.println("------------------------------");
        System.out.println();
    }

    private static String findNameOfBidderWith(String id) {
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
