package ravendbeventstore.infrastructure;

import java.util.UUID;

public class DomainEvent {
    public UUID aggregateId;

    public DomainEvent(UUID aggregateId) {this.aggregateId = aggregateId;}

    public UUID getAggregateId() {
        return aggregateId;
    }

    public DomainEvent setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
        return this;
    }
}
