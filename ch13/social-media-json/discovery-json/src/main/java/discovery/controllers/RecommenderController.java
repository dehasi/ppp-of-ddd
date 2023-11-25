package discovery.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(path = "/api/recommender")
class RecommenderController {

    @RequestMapping(method = GET, path = {"/getrecommendedusers", "/getRecommendedUsers"}, produces = "application/json")
    public ResponseEntity<List<String>> getRecommendedUsers(@RequestParam("accountId") String accountId) throws JsonProcessingException {
        var accountManagementUrl =
                "http://localhost:8080/api/" +
                        "followerdirectory/getusersfollowers?" +
                        "accountId=" + accountId;
        var response = new RestTemplate()
                .getForObject(accountManagementUrl, String.class);

        var followers = new ObjectMapper().readValue(response, new TypeReference<List<Follower>>() {});
        return ResponseEntity.status(200).body(findRecommendedUsersBasedOnSocialTags(followers));
    }

    private static List<String> findRecommendedUsersBasedOnSocialTags(List<Follower> followers) {
        return followers.stream()
                .flatMap(f -> f.tags().stream())
                .distinct()
                .toList();
    }

    public record Follower(String id, String name, List<String> tags) {}
}
