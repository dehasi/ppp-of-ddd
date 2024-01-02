package ravendbexample.infrastructure;

import net.ravendb.client.documents.indexes.AbstractIndexCreationTask;


public class BidHistory_NumberOfBids extends AbstractIndexCreationTask {

    public BidHistory_NumberOfBids() {

        map = """
                docs.bids.Select( bid =>  new {
                    bid.auctionId, count = 1
                })""";

        reduce = """
                results.GroupBy( result => result.auctionId)
                    .Select( g => new {
                        auctionId = g.Key,
                        count = Enumerable.Sum(g, x => ((int) x.count))
                    })""";
    }

    public record ReduceResult(String auctionId ,int count){}
//    public static class ReduceResult {
//        public String auctionId;
//        public int count;
//
//        public String getAuctionId() {
//            return auctionId;
//        }
//
//        public ReduceResult setAuctionId(String auctionId) {
//            this.auctionId = auctionId;
//            return this;
//        }
//
//        public int getCount() {
//            return count;
//        }
//
//        public ReduceResult setCount(int count) {
//            this.count = count;
//            return this;
//        }
//    }
}
