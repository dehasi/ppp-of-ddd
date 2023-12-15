package ch12.listing4;

import java.util.List;

class Couriers {
    static sealed abstract class Courier {}

    static final class AirMail extends Courier {
        public AirMail(List<Item> consignmentItems, DeliveryAddress destination) {}

        public static boolean canDeliver(List<Item> consignmentItems, DeliveryAddress destination) {
            return false;
        }
    }

    static final class StandardMail extends Courier {
        public StandardMail(List<Item> consignmentItems, DeliveryAddress destination) {}
    }

    static final class TrackedService extends Courier {
        public TrackedService(List<Item> consignmentItems, DeliveryAddress destination) {}

        public static boolean canDeliver(List<Item> consignmentItems, DeliveryAddress destination) {
            return false;
        }

    }
}
