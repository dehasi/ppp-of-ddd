package gambling.applicationServices;

import gambling.controllers.ControllerDomain.NewAccount;

import java.util.function.BiConsumer;

public class Domain {
    public interface EventHandler<E> {}

    public interface IReferralPolicy {

        void referralAccepted(BiConsumer<Object, Referral> eventHandler);

        void referralRejected(BiConsumer<Object, Referral> eventHandler);

        void apply(RecommendAFriend command);
    }

    public record Referral(int referrerId, int friendId) {}

    public record RecommendAFriend(int referrerId, NewAccount friend) {}

    public static class ReferralRejectedDueToLongTermOutstandingBalance extends RuntimeException {}
}
