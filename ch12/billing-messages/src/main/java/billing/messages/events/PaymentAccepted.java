package billing.messages.events;

import java.io.Serializable;

public class PaymentAccepted implements Serializable {
    public String orderId;
}
