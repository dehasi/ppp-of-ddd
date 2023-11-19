package promotions.LuckyWinnerSelected;

import java.time.LocalDateTime;
import java.util.List;

public class OrderCreated {

    private String orderId;
    private String userId;
    private List<String> productIds;
    private String shippingTypeId;
    private LocalDateTime timeStamp;
    private double amount;

    public String getOrderId() {
        return orderId;
    }

    public OrderCreated setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public OrderCreated setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public OrderCreated setProductIds(List<String> productIds) {
        this.productIds = productIds;
        return this;
    }

    public String getShippingTypeId() {
        return shippingTypeId;
    }

    public OrderCreated setShippingTypeId(String shippingTypeId) {
        this.shippingTypeId = shippingTypeId;
        return this;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public OrderCreated setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public OrderCreated setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    @Override public String toString() {
        return "OrderCreated{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", productIds=" + productIds +
                ", shippingTypeId='" + shippingTypeId + '\'' +
                ", timeStamp=" + timeStamp +
                ", amount=" + amount +
                '}';
    }
}
