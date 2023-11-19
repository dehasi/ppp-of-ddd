package shipping.ShippingArranged;

import java.util.ArrayList;
import java.util.List;

class ShippingDatabase {

    private static final List<ShippingOrder> ORDERS = new ArrayList<>();

    public static void addOrderDetails(ShippingOrder order) {
        ORDERS.add(order);
    }

    public static String getCustomerAddress(String orderId) {
        var order = ORDERS.stream().filter(o -> o.orderId.equals(orderId)).findFirst().get();

        return String.format(
                "{%s}, Address ID: {%s}",
                order.userId, order.addressId
        );
    }
}
