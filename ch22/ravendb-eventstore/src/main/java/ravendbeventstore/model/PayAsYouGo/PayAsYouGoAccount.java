package ravendbeventstore.model.PayAsYouGo;

import ravendbeventstore.infrastructure.Clock;
import ravendbeventstore.infrastructure.DomainEvent;
import ravendbeventstore.infrastructure.EventSourcedAggregate;

import java.util.UUID;

public class PayAsYouGoAccount extends EventSourcedAggregate {

    private FreeCallAllowance freeCallAllowance;
    private Money credit;
    private final PayAsYouGoInclusiveMinutesOffer inclusiveMinutesOffer = new PayAsYouGoInclusiveMinutesOffer();

    public PayAsYouGoAccount() {}

    public PayAsYouGoAccount(UUID id, Money credit) {
        causes(new AccountCreated(id, credit));
    }

    private PayAsYouGoAccount(PayAsYouGoAccountSnapshot snapShot) {
        // TODO: Restore all state
        super.id = snapShot.id();
        version = snapShot.version();
        super.changes = snapShot.changes();
        credit = snapShot.credit();
        freeCallAllowance = snapShot.freeCallAllowance();
    }

    public static PayAsYouGoAccount from(PayAsYouGoAccountSnapshot snapshot) {
        return new PayAsYouGoAccount(snapshot);
    }

    public PayAsYouGoAccountSnapshot snapshot() {
        return new PayAsYouGoAccountSnapshot(
                id,
                version,
                changes,
                freeCallAllowance,
                credit
        );
    }

    public void topUp(Money credit, Clock clock) {
        if (inclusiveMinutesOffer.isSatisfiedBy(credit))
            causes(new CreditSatisfiesFreeCallAllowanceOffer(this.id, clock.time(), inclusiveMinutesOffer.freeMinutes()));

        causes(new CreditAdded(this.id, credit));
    }

    public void record(PhoneCall phoneCall, PhoneCallCosting phoneCallCosting, Clock clock) {
        var numberOfMinutesCoveredByAllowance = new Minutes(0);

        if (freeCallAllowance != null)
            numberOfMinutesCoveredByAllowance = freeCallAllowance.minutesWhichCanCover(phoneCall, clock);

        var numberOfMinutesToChargeFor = phoneCall.minutes().subtract(numberOfMinutesCoveredByAllowance);

        var costOfCall = phoneCallCosting.determineCostOfCall(numberOfMinutesToChargeFor);

        causes(new PhoneCallCharged(this.id, phoneCall, costOfCall, numberOfMinutesCoveredByAllowance));
    }

    public FreeCallAllowance getFreeCallAllowance() {
        return freeCallAllowance;
    }

    public PayAsYouGoAccount setFreeCallAllowance(FreeCallAllowance freeCallAllowance) {
        this.freeCallAllowance = freeCallAllowance;
        return this;
    }

    public Money getCredit() {
        return credit;
    }

    public PayAsYouGoAccount setCredit(Money credit) {
        this.credit = credit;
        return this;
    }

    private void causes(DomainEvent event) {
        super.changes.add(event);
        apply(event);
    }

    public void apply(DomainEvent event) {
        switch (event) {
            case AccountCreated e -> when(e);
            case CreditAdded e -> when(e);
            case CreditSatisfiesFreeCallAllowanceOffer e -> when(e);
            case PhoneCallCharged e -> when(e);
            default -> throw new IllegalStateException("Unknown event: " + event);
        }
    }

    private void when(CreditAdded creditAdded) {
        credit = credit.add(creditAdded.credit);
    }

    private void when(CreditSatisfiesFreeCallAllowanceOffer creditSatisfiesFreeCallAllowanceOffer) {
        freeCallAllowance = new FreeCallAllowance(creditSatisfiesFreeCallAllowanceOffer.freeMinutes, creditSatisfiesFreeCallAllowanceOffer.offerSatisfied);
    }

    private void when(PhoneCallCharged phoneCallCharged) {
        credit = credit.subtract(phoneCallCharged.costOfCall);

        if (freeCallAllowance != null)
            freeCallAllowance.subtract(phoneCallCharged.coveredByAllowance);
    }

    private void when(AccountCreated accountCreated) {
        id = accountCreated.aggregateId;
        credit = accountCreated.credit;
    }
}
