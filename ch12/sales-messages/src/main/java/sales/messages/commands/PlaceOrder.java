package sales.messages.commands;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PlaceOrder implements Serializable {

    public String userId;
    public String[] productIds;
    public String shippingTypeId;
    public LocalDateTime timeStamp;
}
