package ch19.listings.model.listingFormat.auctions;

import ch19.listings.model.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public record Offer(UUID bidder, Money maximumBid, LocalDateTime timeOfOffer) {}
