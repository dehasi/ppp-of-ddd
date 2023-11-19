package sales.messages.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderCreated implements Serializable {
    public String orderId;
    public String userId;
    public String[] productIds;
    public String shippingTypeId;
    public LocalDateTime timeStamp;
    public double amount;
}
