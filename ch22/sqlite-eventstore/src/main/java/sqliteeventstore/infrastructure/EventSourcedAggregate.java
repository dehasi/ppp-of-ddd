package sqliteeventstore.infrastructure;

import java.util.ArrayList;
import java.util.List;

public abstract class EventSourcedAggregate extends Entity {

    public List<DomainEvent> changes = new ArrayList<>();
    public int version;

    public abstract void apply(DomainEvent changes);

    public List<DomainEvent> getChanges() {
        return changes;
    }

    public EventSourcedAggregate setChanges(List<DomainEvent> changes) {
        this.changes = changes;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public EventSourcedAggregate setVersion(int version) {
        this.version = version;
        return this;
    }

}
