package sales.orderCreated;

class Database {

    private static int id = 0;

     static String saveOrder(String[] productIds, String userId, String shippingTypeId) {
        var nextOrderId = ++id; // I just like "++id" more than "id++"
        return String.valueOf(id);
    }
}
