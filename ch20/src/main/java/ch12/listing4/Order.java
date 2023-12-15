package ch12.listing4;


import java.util.List;

class Order {

    Consignment createFor(List<Item> items, DeliveryAddress deliveryAddress) {
        var courier = CourierFactory.GetCourierFor(items, deliveryAddress);

        var consignment = new Consignment(items, deliveryAddress, courier);

        setAsDispatched(items, consignment);

        return consignment;
    }

    private void setAsDispatched(List<Item> items, Consignment consignment) {}
}
