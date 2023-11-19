package billing.PaymentAccepted;

import billing.messages.commands.PaymentStatus;

class PaymentProvider {
    private static int attempts = 0;

    public static PaymentConfirmation chargeCreditCard(CardDetails details, double amount) {
        if (attempts < 2) {
            attempts++;
            throw new RuntimeException("Service unavailable. Down for maintenance.");
        }
        return new PaymentConfirmation(PaymentStatus.Accepted);
    }
}
