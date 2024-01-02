/*
 * This file is generated by jOOQ.
 */
package jooqexample.infrastructure.jooq.tables.records;


import jooqexample.infrastructure.jooq.tables.Auctions;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuctionsRecord extends UpdatableRecordImpl<AuctionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>auctions.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>auctions.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>auctions.auction_ends</code>.
     */
    public void setAuctionEnds(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>auctions.auction_ends</code>.
     */
    public LocalDateTime getAuctionEnds() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>auctions.bidder_member_id</code>.
     */
    public void setBidderMemberId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>auctions.bidder_member_id</code>.
     */
    public String getBidderMemberId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>auctions.bid</code>.
     */
    public void setBid(Double value) {
        set(3, value);
    }

    /**
     * Getter for <code>auctions.bid</code>.
     */
    public Double getBid() {
        return (Double) get(3);
    }

    /**
     * Setter for <code>auctions.current_price</code>.
     */
    public void setCurrentPrice(Double value) {
        set(4, value);
    }

    /**
     * Getter for <code>auctions.current_price</code>.
     */
    public Double getCurrentPrice() {
        return (Double) get(4);
    }

    /**
     * Setter for <code>auctions.maximum_bid</code>.
     */
    public void setMaximumBid(Double value) {
        set(5, value);
    }

    /**
     * Getter for <code>auctions.maximum_bid</code>.
     */
    public Double getMaximumBid() {
        return (Double) get(5);
    }

    /**
     * Setter for <code>auctions.time_of_bid</code>.
     */
    public void setTimeOfBid(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>auctions.time_of_bid</code>.
     */
    public LocalDateTime getTimeOfBid() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuctionsRecord
     */
    public AuctionsRecord() {
        super(Auctions.AUCTIONS);
    }

    /**
     * Create a detached, initialised AuctionsRecord
     */
    public AuctionsRecord(String id, LocalDateTime auctionEnds, String bidderMemberId, Double bid, Double currentPrice, Double maximumBid, LocalDateTime timeOfBid) {
        super(Auctions.AUCTIONS);

        setId(id);
        setAuctionEnds(auctionEnds);
        setBidderMemberId(bidderMemberId);
        setBid(bid);
        setCurrentPrice(currentPrice);
        setMaximumBid(maximumBid);
        setTimeOfBid(timeOfBid);
        resetChangedOnNotNull();
    }
}