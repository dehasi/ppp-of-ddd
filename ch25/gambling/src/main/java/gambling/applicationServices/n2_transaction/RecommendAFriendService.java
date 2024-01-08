package gambling.applicationServices.n2_transaction;

import gambling.applicationServices.utils.Transaction;
import gambling.controllers.ControllerDomain.NewAccount;

class RecommendAFriendService {

    public void recommendAFriend(int referrerId, NewAccount friendsAccountDetails) {
        validate(friendsAccountDetails);

        // ...
        try (var transaction = Transaction.start()) {
            // ... interact with domain multiple times
            transaction.complete();
        } catch (Exception e) {
            // transaction will roll back if Complete() not called
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
}
