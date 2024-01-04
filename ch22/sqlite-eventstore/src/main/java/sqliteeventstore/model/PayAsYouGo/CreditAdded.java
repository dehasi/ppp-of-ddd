package sqliteeventstore.model.PayAsYouGo;

import sqliteeventstore.infrastructure.DomainEvent;

import java.util.UUID;

public class CreditAdded extends DomainEvent {

    public Money credit;

    public CreditAdded() {} // for Jackson

    public CreditAdded(UUID aggregateId, Money credit) {
        super(aggregateId);
        this.credit = credit;
    }

    public Money getCredit() {
        return credit;
    }

    public CreditAdded setCredit(Money credit) {
        this.credit = credit;
        return this;
    }
}
