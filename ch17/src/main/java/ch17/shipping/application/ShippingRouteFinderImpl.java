package ch17.shipping.application;

import ch17.shipping.model.Location;
import ch17.shipping.model.Route;
import ch17.shipping.model.ShippingRouteFinder;

import java.time.LocalDateTime;

// implementation of Domain Service - this would live in the Service Layer
class ShippingRouteFinderImpl implements ShippingRouteFinder {

    @Override public Route findFastestRoute(Location departing, Location destination, LocalDateTime departureDate) {
        // this method makes HTTP call; best to keep out of the domain
        var response = queryRoutingApi(departing, destination, departureDate);

        var route = parseRoute(response);

        return route;
    }

    private String queryRoutingApi(Location departing, Location destination, LocalDateTime departureDate) {
        // http calls etc
        return null;
    }

    private Route parseRoute(String apiResponse) {
        return null;
    }
}
