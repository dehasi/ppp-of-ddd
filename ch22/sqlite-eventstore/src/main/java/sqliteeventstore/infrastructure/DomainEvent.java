package sqliteeventstore.infrastructure;

import java.util.UUID;

public class DomainEvent {
    public UUID aggregateId;

    public DomainEvent() {} // for Jackson

    public DomainEvent(UUID aggregateId) {this.aggregateId = aggregateId;}

    public UUID getAggregateId() {
        return aggregateId;
    }

    public DomainEvent setAggregateId(String aggregateId) {
        this.aggregateId = UUID.fromString(aggregateId);
        return this;
    }
//
//    public  Class<?> type() {
//        return this.getClass().getCanonicalName();
//    }
}
