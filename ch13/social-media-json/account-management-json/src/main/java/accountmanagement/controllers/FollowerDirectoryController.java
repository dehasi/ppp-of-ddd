package accountmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(path = {"/api/followerDirectory", "/api/followerdirectory"})
class FollowerDirectoryController {

    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = GET, path = {"/getUsersFollowers", "/getusersfollowers"}, produces = "application/json")
    ResponseEntity<String> GetUsersFollowers(@RequestParam("accountId") String accountId) {
        var followers = generateDummyFollowers();

        var body = json(followers);

        return ResponseEntity.status(200).body(body);
    }

    private String json(Object followers) {
        try {
            return mapper.writeValueAsString(followers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Follower> generateDummyFollowers() {
        List<Follower> followers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Follower follower = new Follower("follower_" + i, "happy follower " + i, Set.of("programming", "DDD", "Psychology"));

            followers.add(follower);
        }
        return followers;
    }

    public record Follower(String id, String name, Set<String> tags) {}
}
