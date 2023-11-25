package accountmanagement.controllers;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AccountsController {

    private static final String ENTRY_POINT_BASE_URL = "http://localhost:8080";
    private static final String ACCOUNTS_BASE_URL = "http://localhost:8081/account-management";

    @GetMapping("/account-management/accounts")
    AccountsRepresentation index() {
        return new AccountsRepresentation();
    }

    @GetMapping("/account-management/accounts/{accountId}")
    AccountRepresentation account(@PathVariable("accountId") String accountId) {
        return new AccountRepresentation(accountId);
    }

    public static class AccountsRepresentation extends RepresentationModel {
        public AccountsRepresentation() {
            var href = ACCOUNTS_BASE_URL + "/accounts";
            var rel = IanaLinkRelations.SELF.value();
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "/accounts?page=1";
            rel = IanaLinkRelations.ALTERNATE.value();
            add(Link.of(href, rel));

            // automatically identified as a template
            href = ACCOUNTS_BASE_URL + "/accounts/{accountId}";
            rel = "account";
            var title = "account template";
            add(Link.of(href, rel).withTitle(title));

            href = ACCOUNTS_BASE_URL + "/accounts/123";
            rel = "account";
            title = "account 123";
            add(Link.of(href, rel).withTitle(title));

            href = ACCOUNTS_BASE_URL + "/accounts/456";
            rel = "account";
            title = "account 456";
            add(Link.of(href, rel).withTitle(title));

            href = ACCOUNTS_BASE_URL + "/accounts?page=2";
            rel = IanaLinkRelations.NEXT.value();
            add(Link.of(href, rel));

            href = ENTRY_POINT_BASE_URL + "/account-management";
            rel = "parent";
            add(Link.of(href, rel));
        }
    }

    public static class AccountRepresentation extends RepresentationModel {
        public final String accountId;
        public final String name;

        public AccountRepresentation(String accountId) {
            this.accountId = accountId;
            this.name = "Account_" + accountId;

            var href = ACCOUNTS_BASE_URL + "/accounts/" + accountId;
            var rel = IanaLinkRelations.SELF.value();
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "/accounts";
            rel = "collection";
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "/accounts/" + accountId + "/followers";
            rel = "followers";
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "/accounts/" + accountId + "/following";
            rel = "following";
            add(Link.of(href, rel));

            href = ACCOUNTS_BASE_URL + "/accounts/" + accountId + "/blurbs";
            rel = "blurbs";
            add(Link.of(href, rel));
        }
    }
}
