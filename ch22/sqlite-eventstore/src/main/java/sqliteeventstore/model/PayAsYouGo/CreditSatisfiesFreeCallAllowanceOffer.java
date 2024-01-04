package sqliteeventstore.model.PayAsYouGo;

import sqliteeventstore.infrastructure.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreditSatisfiesFreeCallAllowanceOffer extends DomainEvent {

    public LocalDateTime offerSatisfied;
    public Minutes freeMinutes;

    public CreditSatisfiesFreeCallAllowanceOffer() {}

    public CreditSatisfiesFreeCallAllowanceOffer(UUID aggregateId, LocalDateTime offerSatisfied, Minutes freeMinutes) {
        super(aggregateId);
        this.offerSatisfied = offerSatisfied;
        this.freeMinutes = freeMinutes;
    }

    public LocalDateTime getOfferSatisfied() {
        return offerSatisfied;
    }

    public CreditSatisfiesFreeCallAllowanceOffer setOfferSatisfied(LocalDateTime offerSatisfied) {
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
