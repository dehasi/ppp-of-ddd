package ch12.listing4;

import java.util.List;

record Consignment(List<Item> items, DeliveryAddress destination, Couriers.Courier courier) {

}
