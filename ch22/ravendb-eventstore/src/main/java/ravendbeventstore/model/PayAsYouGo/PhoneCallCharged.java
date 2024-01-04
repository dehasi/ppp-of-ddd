package ravendbeventstore.model.PayAsYouGo;

import ravendbeventstore.infrastructure.DomainEvent;

import java.util.UUID;

class PhoneCallCharged extends DomainEvent {

    public PhoneCall phoneCall;
    public Money costOfCall;
    public Minutes coveredByAllowance;

    public PhoneCallCharged(UUID aggregateId, PhoneCall phoneCall, Money costOfCall, Minutes coveredByAllowance) {
        super(aggregateId);
        this.phoneCall = phoneCall;
        this.costOfCall = costOfCall;
        this.coveredByAllowance = coveredByAllowance;
    }

    public PhoneCall getPhoneCall() {
        return phoneCall;
    }

    public PhoneCallCharged setPhoneCall(PhoneCall phoneCall) {
        this.phoneCall = phoneCall;
        return this;
    }

    public Money getCostOfCall() {
        return costOfCall;
    }

    public PhoneCallCharged setCostOfCall(Money costOfCall) {
        this.costOfCall = costOfCall;
        return this;
    }

    public Minutes getCoveredByAllowance() {
        return coveredByAllowance;
    }

    public PhoneCallCharged setCoveredByAllowance(Minutes coveredByAllowance) {
        this.coveredByAllowance = coveredByAllowance;
        return this;
    }
}
