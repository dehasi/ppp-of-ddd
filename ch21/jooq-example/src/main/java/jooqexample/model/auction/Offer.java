package jooqexample.model.auction;

import java.time.LocalDateTime;
import java.util.UUID;

public record Offer(
        UUID bidder,
        Money maximumBid,
        LocalDateTime timeOfOffer
) {}
