package eventstoredb.model.PayAsYouGo;

import eventstoredb.infrastructure.DomainEvent;

import java.util.UUID;

public class AccountCreated extends DomainEvent {
    public Money credit;

    public AccountCreated() {} // for Jackson

    public AccountCreated(UUID aggregateId, Money credit) {
        super(aggregateId);
        this.credit = credit;
    }

    public AccountCreated setCredit(Money credit) {
        this.credit = credit;
        return this;
    }

    public Money getCredit() {
        return credit;
    }
}
