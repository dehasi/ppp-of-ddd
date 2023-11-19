package billing.messages.commands;

import java.io.Serializable;

public class RecordPaymentAttempt implements Serializable {

    public String orderId;
    public PaymentStatus status;
}
