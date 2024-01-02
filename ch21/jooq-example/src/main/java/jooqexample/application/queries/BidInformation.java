package jooqexample.application.queries;

import java.time.LocalDateTime;
import java.util.UUID;

public record BidInformation(
        UUID bidder,
        double amountBid,
        LocalDateTime timeOfBid) {
}
