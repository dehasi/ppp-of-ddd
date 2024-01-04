package eventstoredb.model.PayAsYouGo;

import eventstoredb.infrastructure.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreditSatisfiesFreeCallAllowanceOffer extends DomainEvent {

    public String offerSatisfied;
    public Minutes freeMinutes;

    public CreditSatisfiesFreeCallAllowanceOffer() {}

    public CreditSatisfiesFreeCallAllowanceOffer(UUID aggregateId, LocalDateTime offerSatisfied, Minutes freeMinutes) {
        super(aggregateId);
        this.offerSatisfied = offerSatisfied.toString();
        this.freeMinutes = freeMinutes;
    }

    public String getOfferSatisfied() {
        return offerSatisfied;
    }

    public CreditSatisfiesFreeCallAllowanceOffer setOfferSatisfied(String offerSatisfied) {
        this.offerSatisfied = offerSatisfied;
        return this;
    }

    public Minutes getFreeMinutes() {
        return freeMinutes;
    }

    public CreditSatisfiesFreeCallAllowanceOffer setFreeMinutes(Minutes freeMinutes) {
        this.freeMinutes = freeMinutes;
        return this;
    }
}
