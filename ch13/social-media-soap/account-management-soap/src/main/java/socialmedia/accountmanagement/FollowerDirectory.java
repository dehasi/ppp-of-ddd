package socialmedia.accountmanagement;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Endpoint
class FollowerDirectory {
    private static final String NAMESPACE_URI = "http://accountmanagement.socialmedia";

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findUsersFollowers")
    FindUsersFollowersResponse findUsersFollowers(@RequestPayload FindUsersFollowers request) {
        FindUsersFollowersResponse response = new FindUsersFollowersResponse();
        response.getFollower().addAll(generateDummyFollowers());
        return response;

    }

    private static List<Follower> generateDummyFollowers() {
        List<Follower> followers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Follower follower = new Follower();
            follower.setId("follower_" + i);
            follower.setName("happy follower " + i);
            follower.getTags().addAll(Set.of("programming", "DDD", "Psychology"));
            followers.add(follower);
        }
        return followers;
    }
}
