package ravendbeventstore.infrastructure;

import net.ravendb.client.documents.session.IDocumentSession;

import java.time.LocalDateTime;
import java.util.List;

public class EventStore implements IEventStore {

    private final IDocumentSession documentSession;

    public EventStore(IDocumentSession documentSession) {this.documentSession = documentSession;}

    @Override public void createNewStream(String streamName, List<DomainEvent> domainEvents) {
        var eventStream = new EventStream(streamName);
        documentSession.store(eventStream);
        appendEventsToStream(streamName, domainEvents, null);
    }

    @Override public void appendEventsToStream(String streamName, List<DomainEvent> domainEvents, Integer expectedVersion) {
        var stream = documentSession.load(EventStream.class, streamName);

        // THIS IS NOT THREAD SAFE
        if (expectedVersion != null) checkForConcurrencyError(expectedVersion, stream);
        // Even from another thread can be added here: after "checkForConcurrencyError" and before "store".
        // RavenDB.setUseOptimisticConcurrency(true); solves it.
        for (var event : domainEvents) {
            documentSession.store(stream.registerEvent(event));
        }
    }

    @Override public List<DomainEvent> getStream(String streamName, int fromVersion, int toVersion) {
        var eventWrappers = documentSession.query(EventWrapper.class)
                .waitForNonStaleResults()
                .whereEquals("eventStreamId", streamName)
                .whereLessThanOrEqual("eventNumber", toVersion)
                .whereGreaterThanOrEqual("eventNumber", fromVersion)
                .orderBy("eventNumber")
                .toList();

        return eventWrappers.stream()
                .map(EventWrapper::getEvent)
                .toList();
    }

    @Override public <T> void addSnapshot(String streamName, T snapshot) {
        var wrapper = new SnapshotWrapper(
                streamName, snapshot, LocalDateTime.now()
        );
        documentSession.store(wrapper);
    }

    @SuppressWarnings("unchecked") // We expect a certain type
    @Override public <T> T getLatestSnapshot(String streamName) {
        var latestSnapshot = documentSession.query(SnapshotWrapper.class)
                .waitForNonStaleResults()
                .whereEquals(streamName, streamName)
                .orderByDescending("created")
                .firstOrDefault();

        if (latestSnapshot == null) {
            return null; // Maybe make the return type Optional<T>?
        }
        return (T) latestSnapshot.snapshot();
    }

    private static void checkForConcurrencyError(int expectedVersion, EventStream stream) {
        var lastUpdatedVersion = stream.version;
        if (lastUpdatedVersion != expectedVersion) {
            var message = String.format("Expected: {%s}. Found: {%s}", expectedVersion, lastUpdatedVersion);
            throw new OptimisticConcurrencyException(message);
        }
    }
}
