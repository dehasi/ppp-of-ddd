package gambling.applicationServices.n6_communication;

import gambling.applicationServices.n6_communication.RecommendAFriendService.GamblingMessagesNamespace.CustomerRegisteredViaReferralPolicy;
import gambling.applicationServices.n6_communication.RecommendAFriendService.GamblingMessagesNamespace.ReferralSignupRejected;
import gambling.applicationServices.utils.ApplicationEventPublisher;
import gambling.applicationServices.utils.Transaction;
import gambling.controllers.ControllerDomain.ICustomerDirectory;
import gambling.controllers.ControllerDomain.IReferAFriendPolicy;
import gambling.controllers.ControllerDomain.NewAccount;

import java.util.logging.Logger;

class RecommendAFriendService {

    Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    private final ICustomerDirectory customerDirectory;
    private final IReferAFriendPolicy referAFriendPolicy;
    private final ApplicationEventPublisher applicationEventPublisher;

    RecommendAFriendService(ICustomerDirectory customerDirectory, IReferAFriendPolicy referAFriendPolicy, ApplicationEventPublisher applicationEventPublisher) {
        this.customerDirectory = customerDirectory;
        this.referAFriendPolicy = referAFriendPolicy;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void recommendAFriend(int referrerId, NewAccount friendsAccountDetails) {
        validate(friendsAccountDetails);

        // ...
        try (var transaction = Transaction.start()) {
            var referrer = customerDirectory.Find(referrerId);
            var friend = customerDirectory.add(friendsAccountDetails);
            referAFriendPolicy.Apply(referrer, friend);

            transaction.complete();

            var msg = new CustomerRegisteredViaReferralPolicy(
                    referrerId,
                    friend.id
            );
            applicationEventPublisher.publishEvent(msg);
        } catch (ReferralRejectedDueToLongTermOutstandingBalance e) {
            var msg = new ReferralSignupRejected(
                    referrerId,
                    friendsAccountDetails.email(),
                    "Referrer has long term outstanding balance"
            );
            applicationEventPublisher.publishEvent(msg);
        }
    }

    // technical validation carried out at the application level
    private void validate(NewAccount account) {
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

    static class GamblingMessagesNamespace {
        public record CustomerRegisteredViaReferralPolicy(int referrerId, int friendId) {}

        public record ReferralSignupRejected(int referrerId, String friendEmail, String reason) {}
    }
}
