package gambling.applicationServices.asyncAwait;

import gambling.controllers.ControllerDomain.ICustomerDirectoryAsync;
import gambling.controllers.ControllerDomain.IReferAFriendPolicy;
import gambling.controllers.ControllerDomain.NewAccount;

import java.util.concurrent.ExecutionException;

class RecommendAFriendService {

    private final ICustomerDirectoryAsync customerDirectory;
    private final IReferAFriendPolicy referAFriendPolicy;

    RecommendAFriendService(ICustomerDirectoryAsync customerDirectory, IReferAFriendPolicy referAFriendPolicy) {
        this.customerDirectory = customerDirectory;
        this.referAFriendPolicy = referAFriendPolicy;
    }

    public void recommendAFriend(int referrerId, NewAccount friendsAccountDetails) throws ExecutionException, InterruptedException {
        validate(friendsAccountDetails);

        // ...
        // ...
        var referrer = customerDirectory.find(referrerId).get();
        var friend = customerDirectory.add(friendsAccountDetails).get();
        // ...
        referAFriendPolicy.Apply(referrer, friend);
        // ...
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
