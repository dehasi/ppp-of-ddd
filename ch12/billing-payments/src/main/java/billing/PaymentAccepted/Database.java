package billing.PaymentAccepted;

import billing.messages.commands.PaymentStatus;

class Database {
    static CardDetails getCardDetailsFor(String userId) {
        return new CardDetails();
    }

    static void savePaymentAttempt(String orderId, PaymentStatus status) {
        // .. save it to your favorite database
    }
}
