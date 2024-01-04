package eventstoredb.infrastructure;

import java.util.UUID;

public class DomainEvent {
    public UUID id;
    public UUID aggregateId;

    public DomainEvent() {} // for Jackson

    public DomainEvent(UUID aggregateId) {
        this.id = UUID.randomUUID();
        this.aggregateId = aggregateId;
    }

    public UUID getId() {
        return id;
    }

    public DomainEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public DomainEvent setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
        return this;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public DomainEvent setAggregateId(String aggregateId) {
        this.aggregateId = UUID.fromString(aggregateId);
        return this;
    }
}
