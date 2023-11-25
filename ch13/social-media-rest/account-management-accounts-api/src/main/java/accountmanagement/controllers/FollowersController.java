package accountmanagement.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
class FollowersController {

    private static final String ACCOUNTS_BASE_URL = "http://localhost:8081/account-management";

    @Autowired
    private EventPersister eventPersister;

    @GetMapping("/account-management/accounts/{accountId}/followers")
    FollowersRepresentation index(@PathVariable("accountId") String accountId) {
        return new FollowersRepresentation(accountId, getFollowers(accountId));
    }

    @PostMapping("/account-management/accounts/{accountId}/followers")
    void indexPOST(@PathVariable("accountId") String accountId, @RequestBody Follower follower, HttpServletResponse response) throws IOException {
        // accountId will be taken from querystring - it is a simple type
        // follower will be taken from request body - it is a complex type
        var event = new BeganFollowing(accountId, follower.accountId());
        eventPersister.persistEvent(event);
        response.sendRedirect("/account-management/accounts/" + accountId + "/followers");
    }

    static class FollowersRepresentation extends RepresentationModel {
        public final List<Follower> followers;

        public FollowersRepresentation(String accountId, List<Follower> followers) {
            this.followers = followers;

            var href = ACCOUNTS_BASE_URL + "/accounts/" + accountId + "/followers";
            var rel = IanaLinkRelations.SELF.value();
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "/accounts/" + accountId + "/followers?pages=2";
            rel = IanaLinkRelations.NEXT.value();
            add(Link.of(href, rel));
        }
    }

    private static List<Follower> getFollowers(String accountId) {
        // replace with DB lookup, etc. (in real application)
        return List.of(
                new Follower("fl1"),
                new Follower("fl2"),
                new Follower("fl3"));
    }

    record Follower(String accountId) {}

    // representing the domain event
    public record BeganFollowing(String accountId, String followerId) {}
}
