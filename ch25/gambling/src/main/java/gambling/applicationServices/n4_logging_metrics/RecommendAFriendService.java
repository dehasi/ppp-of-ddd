package gambling.applicationServices.n4_logging_metrics;

import gambling.applicationServices.utils.StatsDClient;
import gambling.applicationServices.utils.Transaction;
import gambling.controllers.ControllerDomain;
import gambling.controllers.ControllerDomain.ICustomerDirectory;
import gambling.controllers.ControllerDomain.IReferAFriendPolicy;
import gambling.controllers.ControllerDomain.NewAccount;

import java.util.logging.Logger;

class RecommendAFriendService {

    Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    private final ICustomerDirectory customerDirectory;
    private final IReferAFriendPolicy referAFriendPolicy;
    private final StatsDClient statsdClient;

    RecommendAFriendService(ICustomerDirectory customerDirectory, IReferAFriendPolicy referAFriendPolicy, StatsDClient statsdClient) {
        this.customerDirectory = customerDirectory;
        this.referAFriendPolicy = referAFriendPolicy;
        this.statsdClient = statsdClient;
    }

    public void recommendAFriend(int referrerId, NewAccount friendsAccountDetails) {
        validate(friendsAccountDetails);

        // ...
        try (var transaction = Transaction.start()) {
            // customerDirectory is a domain repository
            var referrer = customerDirectory.Find(referrerId);
            var friend = customerDirectory.add(friendsAccountDetails);
            // referAFriend policy is a domain policy
            referAFriendPolicy.Apply(referrer, friend);
            // ... interact with domain multiple times
            transaction.complete();
            logger.info("Successful friend recommendation");
            statsdClient.incrementCounter("friendReferrals");
        } catch (ReferralRejectedDueToLongTermOutstandingBalance e) {
            logger.severe("Successful friend recommendation");
            statsdClient.incrementCounter("ReferralRejected");
            throw new ApplicationError(""
                    + "Sorry, this referral cannot be completed. The referrer"
                    + " currently has an outstanding balance. Please contact"
                    + " Customer Services"
            );
            // transaction will roll back if Complete() not called
        }
    }

    // technical validation carried out at the application level
    private void validate(ControllerDomain.NewAccount account) {
        if (isNullOrWhiteSpace(account.email()))
            throw new ValidationFailure("You must supply an email");

        if (isNullOrWhiteSpace(account.nickname()))
            throw new ValidationFailure("You must supply a Nickname");

        if (!account.email().contains("@"))
            throw new ValidationFailure("Not a valid email address");

        if (account.email().length() >= 50)
            throw new ValidationFailure("Email address must be less than 50 characters");

        if (account.nickname().length() >= 25)
            throw new ValidationFailure("Nickname must be less than 25 characters");
    }

    private static boolean isNullOrWhiteSpace(String string) {
        return string == null || string.isBlank();
    }

    public static class ValidationFailure extends RuntimeException {
        public ValidationFailure(String message) {
            super(message);
        }
    }

    public static class ApplicationError extends RuntimeException {
        public ApplicationError(String message) {super(message);}
    }

    private static class ReferralRejectedDueToLongTermOutstandingBalance extends RuntimeException {}
}
