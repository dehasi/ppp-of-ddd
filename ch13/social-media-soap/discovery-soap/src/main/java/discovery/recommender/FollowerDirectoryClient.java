package discovery.recommender;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import socialmedia.accountmanagement.FindUsersFollowers;
import socialmedia.accountmanagement.FindUsersFollowersResponse;
import socialmedia.accountmanagement.Follower;

import java.util.List;

class FollowerDirectoryClient extends WebServiceGatewaySupport {

    public List<Follower> findUsersFollowersResponse(String accountId) {
        FindUsersFollowers request = new FindUsersFollowers();
        request.setAccountId(accountId);

        try {
            FindUsersFollowersResponse response = (FindUsersFollowersResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(request);
            return response.getFollower();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
