package gambling.applicationServices.publishSubscribeAsync;

import gambling.applicationServices.Domain.IReferralPolicy;
import gambling.applicationServices.Domain.RecommendAFriend;
import gambling.applicationServices.Domain.Referral;
import gambling.controllers.ControllerDomain.NewAccount;

class RecommendAFriendService {

    private final IReferralPolicy policy;

    public RecommendAFriendService(IReferralPolicy policy) {
        // subscribe to events on domain model
        policy.referralAccepted(this::handleReferralAccepted);
        policy.referralAccepted(this::handleReferralRejected);

        this.policy = policy;
    }

    private void handleReferralAccepted(Object sender, Referral e) {
        // send confirmation emails etc
    }

    private void handleReferralRejected(Object sender, Referral e) {
        // send rejection emails etc
    }

    public void recommendAFriend(int referrerId, NewAccount friend) {
        // validation, open transaction etc
        var command = new RecommendAFriend(
                referrerId,
                friend
        );
        Thread.startVirtualThread(() -> policy.apply(command));
        // close transaction - success and failure handled in handlers
    }
}
