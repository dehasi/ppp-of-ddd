package ch17.shipping.model;

import java.time.LocalDateTime;

// interface for Domain Service - this would live in the Domain Model
// this is the "contract"
public interface ShippingRouteFinder {

    Route findFastestRoute(Location departing, Location destination, LocalDateTime departureDate);
}
