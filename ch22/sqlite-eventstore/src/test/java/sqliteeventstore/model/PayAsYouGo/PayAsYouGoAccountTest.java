package sqliteeventstore.model.PayAsYouGo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqliteeventstore.infrastructure.SystemClock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PayAsYouGoAccountTest {

    private final Money fiveDollars = new Money(5);
    private final PayAsYouGoInclusiveMinutesOffer free90MinsWith10DollarTopUp = new PayAsYouGoInclusiveMinutesOffer(new Money(10), new Minutes(90));

    private PayAsYouGoAccount account;

    @BeforeEach void when_applying_a_top_up_that_does_not_qualify_for_inclusive_minutes() {
        account = new PayAsYouGoAccount(UUID.randomUUID(), new Money(0));
        // remove the AccountCreated event that is not relevant to this test
        account.changes.clear();

        account.addInclusiveMinutesOffer(free90MinsWith10DollarTopUp);
        account.topUp(fiveDollars, new SystemClock()); // $5 top up does not meet $10 threshold for free minutes
    }

    @Test void the_account_will_be_credited_with_the_top_up_amount_but_no_free_minutes() {
        assertThat(account.changes).hasSize(1);

        var event = (CreditAdded)account.changes.get(0);
        assertThat(event.credit).isEqualTo(fiveDollars);
        assertThat(account.snapshot().credit().value()).isEqualTo(5);

    }
}