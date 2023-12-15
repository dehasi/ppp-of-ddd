package ch12.listing4;

import ch12.listing4.Couriers.AirMail;
import ch12.listing4.Couriers.StandardMail;
import ch12.listing4.Couriers.TrackedService;

import java.util.List;

class CourierFactory {

    public static Couriers.Courier GetCourierFor(List<Item> consignmentItems, DeliveryAddress destination) {
        if (AirMail.canDeliver(consignmentItems, destination)) {
            return new AirMail(consignmentItems, destination);
        } else if (TrackedService.canDeliver(consignmentItems, destination)) {
            return new TrackedService(consignmentItems, destination);
        } else {
            return new StandardMail(consignmentItems, destination);
        }
    }
}
