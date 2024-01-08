package gambling.applicationServices.publishSubscribe;

import gambling.applicationServices.Domain;
import gambling.applicationServices.Domain.IReferralPolicy;
import gambling.applicationServices.Domain.RecommendAFriend;
import gambling.controllers.ControllerDomain.NewAccount;

public class RecommendAFriendService {

    private final IReferralPolicy policy;

    public RecommendAFriendService(IReferralPolicy policy) {
        // subscribe to events on a domain model
        policy.referralAccepted(this::handleReferralAccepted);
        policy.referralAccepted(this::handleReferralRejected);

        this.policy = policy;
    }

    private void handleReferralAccepted(Object sender, Domain.Referral e) {
        // send confirmation emails etc
    }

    private void handleReferralRejected(Object sender, Domain.Referral e) {
        // send rejection emails etc
    }

    public void referAFriend(int referrerId, NewAccount friend) {
        // validation, open transaction etc
        var command = new RecommendAFriend(
                referrerId,
                friend
        );

        policy.apply(command);
        // close transaction - success and failure handled in handlers
    }

}
