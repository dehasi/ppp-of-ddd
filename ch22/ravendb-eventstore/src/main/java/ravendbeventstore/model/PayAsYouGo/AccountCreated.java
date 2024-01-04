package ravendbeventstore.model.PayAsYouGo;

import ravendbeventstore.infrastructure.DomainEvent;

import java.util.UUID;

public class AccountCreated extends DomainEvent {
    public final Money credit;

    public AccountCreated(UUID aggregateId, Money credit) {
        super(aggregateId);
        this.credit = credit;
    }

    public Money getCredit() {
        return credit;
    }
}
