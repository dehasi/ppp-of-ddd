package gambling.applicationServices.requestReply;

import gambling.applicationServices.Domain;
import gambling.applicationServices.Domain.IReferralPolicy;
import gambling.applicationServices.Domain.RecommendAFriend;
import gambling.controllers.ControllerDomain.NewAccount;

class RecommendAFriendService {

    private final IReferralPolicy policy;

    public RecommendAFriendService(IReferralPolicy policy) {
        this.policy = policy;
    }

    public RecommendAFriendResponse recommendAFriend(RecommendAFriendRequest request) {
        try {
            var command = new RecommendAFriend(request.referrerId(), request.friend());

            policy.apply(command);

            return new RecommendAFriendResponse(RecommendAFriendStatus.Success);
        } catch (Domain.ReferralRejectedDueToLongTermOutstandingBalance e) {
            return new RecommendAFriendResponse(RecommendAFriendStatus.ReferralRejected);
        }
    }

    record RecommendAFriendRequest(int referrerId, NewAccount friend) {}

    record RecommendAFriendResponse(RecommendAFriendStatus status) {}

    enum RecommendAFriendStatus {
        Success,
        ReferralRejected
    }
}
