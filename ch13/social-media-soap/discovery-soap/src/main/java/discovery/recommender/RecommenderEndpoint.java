package discovery.recommender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import socialmedia.accountmanagement.Follower;

import java.util.List;

@Endpoint
class RecommenderEndpoint {

    @Autowired
    private FollowerDirectoryClient client;

    @ResponsePayload
    @PayloadRoot(namespace = "http://recommender.discovery", localPart = "GetRecommendedUsers")
    RecommendedUsersResponse getRecommendedUsers(@RequestPayload GetRecommendedUsers request) {
        var followers = client.findUsersFollowersResponse(request.getAccountId());

        var response = new RecommendedUsersResponse();
        response.getTags().addAll(findRecommendedUsersBasedOnSocialTags(followers));
        return response;
    }

    private List<String> findRecommendedUsersBasedOnSocialTags(
            List<Follower> followers) {
        /*
         * Real system would look at the users tags and find
         * popular accounts with similar tags by making more
         * RPC calls etc.
         */
        return followers.stream()
                .flatMap(f -> f.getTags().stream())
                .distinct()
                .toList();
    }
}
