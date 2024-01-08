package gambling.applicationServices.n5_authentication;

class AdminRecommendAFriendService {

    private final IAuthenticationService authentication;
    private final IAuthorizationService authorization;

    public AdminRecommendAFriendService(IAuthenticationService authentication,
                                        IAuthorizationService authorization) {
        this.authentication = authentication;
        this.authorization = authorization;
    }

    public void referAFriend(int referrerId, int friendId) {
        if (!authentication.isLoggedInUser())
            throw new AuthenticationError();

        if (!authorization.isCurrentUserAdmin())
            throw new AuthorizationError();

        // look up customers

        // apply referral policy
    }

    public interface IAuthenticationService {
        boolean isLoggedInUser();
    }

    public interface IAuthorizationService {
        boolean isCurrentUserAdmin();
    }

    static class AuthorizationError extends RuntimeException {}

    static class AuthenticationError extends RuntimeException {}
}
