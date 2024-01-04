package ravendbeventstore.infrastructure;

import java.util.List;

public interface IEventStore { // <T>?

    void createNewStream(String streamName, List<DomainEvent> domainEvents);

    default void appendEventsToStream(String streamName, List<DomainEvent> domainEvents) {
        appendEventsToStream(streamName, domainEvents, null);
    }

    void appendEventsToStream(String streamName, List<DomainEvent> domainEvents, Integer expectedVersion);

    List<DomainEvent> getStream(String streamName, int fromVersion, int toVersion);

    <T> void addSnapshot(String streamName, T snapshot);

    <T> T getLatestSnapshot(String streamName); //  where T:class; maybe (Class<?> type, String streamName)
}
