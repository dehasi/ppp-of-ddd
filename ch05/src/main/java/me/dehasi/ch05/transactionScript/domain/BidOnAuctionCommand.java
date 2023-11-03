package me.dehasi.ch05.transactionScript.domain;

import me.dehasi.replacements.exception.ApplicationException;
import me.dehasi.replacements.exception.InvalidOperationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;
import static zeliba.the.TheComparable.the;

public class BidOnAuctionCommand implements ICommand {

    private UUID auctionId;
    private UUID bidderId;
    private BigDecimal amount;
    private LocalDateTime timeOfBid;

    public BidOnAuctionCommand(UUID auctionId, UUID bidderId, BigDecimal amount, LocalDateTime timeOfBid) {
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.amount = amount;
        this.timeOfBid = timeOfBid;
    }

    @Override public void execute() {
        try (TransactionScope scope = new TransactionScope()) {
            throwExceptionIfNotValid(auctionId, bidderId, amount, timeOfBid);

            throwExceptionIfAuctionHasEnded(auctionId);

            if (isFirstBid(auctionId))
                placeFirstBid(auctionId, bidderId, amount, timeOfBid);
            else if (isIncreasingMaximimBid(auctionId, amount, bidderId))
                increaseMaximumBidTo(amount);
            else if (canMeetOrExceedBidIncrement(amount))
                updatePrice(auctionId, bidderId, amount, timeOfBid);
        }
    }


    private void throwExceptionIfAuctionHasEnded(UUID auctionId) {
        // check at DB
    }

    private boolean canMeetOrExceedBidIncrement(BigDecimal amount) {
        return true;
    }

    private void updatePrice(UUID auctionId, UUID bidderId, BigDecimal amount, LocalDateTime timeOfBi) {}

    private void increaseMaximumBidTo(BigDecimal amount) {}

    private boolean isIncreasingMaximimBid(UUID auctionId, BigDecimal amount, UUID bidderId) {
        return true;
    }

    private boolean isFirstBid(UUID auctionId) {
        return true;
    }

    private void placeFirstBid(UUID auctionId, UUID bidderId, BigDecimal amount, LocalDateTime timeOfBid) {}

    private void firstBid(Auction auction, UUID bidderId, BigDecimal amount, LocalDateTime dateOfBid) {
        if (the(amount).isLessThan(auction.startingPrice)) {
            auction.winningBidder = bidderId;
            auction.winningBid = amount;

            // DB Update
        } else throw new ApplicationException("You have to bid greater than the starting price.");
    }

    private void throwExceptionIfNotValid(UUID auctionId, UUID bidderId, BigDecimal amount, LocalDateTime dateOfBid) {
        requireNonNull(auctionId, "AuctionId cannot be null");
        requireNonNull(bidderId, "BidderId cannot be null");

        if (dateOfBid == LocalDateTime.MIN)
            throw new IllegalArgumentException("Time of bid must have a value");

        if (amount.stripTrailingZeros().scale() > 2)
            throw new InvalidOperationException("There cannot be more than two decimal places.");

        if (the(amount).isLessThan(ZERO))
            throw new InvalidOperationException("Money cannot be a negative value.");
    }

    private BigDecimal bidIncrement(BigDecimal currentAuctionWinningBid) {
        if (the(currentAuctionWinningBid).isGreaterOrEqualsThan(new BigDecimal("0.01"))
                && the(currentAuctionWinningBid).isLessOrEqualsThan(new BigDecimal("0.99")))
            return new BigDecimal("0.05");

        if (the(currentAuctionWinningBid).isGreaterOrEqualsThan(new BigDecimal("1.00"))
                && the(currentAuctionWinningBid).isLessOrEqualsThan(new BigDecimal("4.99")))
            return new BigDecimal("0.20");

        if (the(currentAuctionWinningBid).isGreaterOrEqualsThan(new BigDecimal("5.00"))
                && the(currentAuctionWinningBid).isLessOrEqualsThan(new BigDecimal("14.99")))
            return new BigDecimal("0.50");

        return new BigDecimal("1.00");
    }

    // C# Specific
    // Namespace: System.Transactions
    // Assembly: System.Transactions.Local.dll
    // Makes a code block transactional.
    private static class TransactionScope implements AutoCloseable {
        @Override public void close() {}
    }
}
