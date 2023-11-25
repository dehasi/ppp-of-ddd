package accountmanagement.controllers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EntryPointController {

    private static final String ENTRY_POINT_BASE_URL = "http://localhost:8080/";
    private static final String ACCOUNTS_BASE_URL = "http://localhost:8081/";
    private static final String BEGAN_FOLLOWING_BASE_URL = "http://localhost:8082/";

    @GetMapping("/account-management")
    EntryPointRepresentation get() throws NoSuchMethodException {
        return new EntryPointRepresentation();
    }

    public static class EntryPointRepresentation extends RepresentationModel {
        public EntryPointRepresentation() {
            var href = ENTRY_POINT_BASE_URL + "account-management";
            var rel = "self";
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "account-management/accounts";
            rel = "accounts";
            add(Link.of(href, rel));

            href = BEGAN_FOLLOWING_BASE_URL+ "account-management/began-following";
            rel = "beganfollowing";
            add(Link.of(href, rel));
        }
    }
}
