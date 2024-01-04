package ravendbeventstore.model.PayAsYouGo;

import ravendbeventstore.infrastructure.DomainEvent;

import java.util.UUID;

public class CreditAdded extends DomainEvent {

    public final Money credit;

    public CreditAdded(UUID aggregateId, Money credit) {
        super(aggregateId);
        this.credit = credit;
    }

    public Money getCredit() {
        return credit;
    }
}
