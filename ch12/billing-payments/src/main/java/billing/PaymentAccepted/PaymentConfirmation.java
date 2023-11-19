package billing.PaymentAccepted;

import billing.messages.commands.PaymentStatus;

class PaymentConfirmation {

    PaymentStatus status;

    public PaymentConfirmation(PaymentStatus status) {
        this.status = status;
    }
}
