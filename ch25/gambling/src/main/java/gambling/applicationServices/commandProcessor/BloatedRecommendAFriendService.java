package gambling.applicationServices.commandProcessor;

import gambling.controllers.ControllerDomain.NewAccount;

class BloatedRecommendAFriendService {

    public void recommendAFriend(int referrerId, NewAccount friend) {
        // ...
    }

    public void recommendAFriendInDifferentCountry(int referrerId, NewAccount friend) {
        // ...
    }

    public void reverseFriendReferral(int referrerId, int friendId) {
        // ...
    }

    public void referAFriendWithoutLoyaltyBonus(int referrerId, NewAccount friend) {
        // ...
    }

    // .... more methods like this

    static class CommandProcessorNamespace {
        // command expressing intent
        public record RecommendAFriend(int referrerId, NewAccount friend) {}

        public interface IRecommendAFriendProcessor {
            void process(RecommendAFriend command);
        }
    }
}
