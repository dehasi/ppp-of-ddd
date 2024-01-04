package eventstoredb.infrastructure;

import com.eventstore.dbclient.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EventStore implements IEventStore {

    private static final String EVENT_CLR_TYPE_HEADER = "EventClrTypeName";
    private final EventStoreDBClient eventStoreDBClient;
    private final ObjectMapper objectMapper;

    public EventStore(EventStoreDBClient eventStoreDBClient, ObjectMapper objectMapper) {
        this.eventStoreDBClient = eventStoreDBClient;
        this.objectMapper = objectMapper;
    }


    @Override public void createNewStream(String streamName, List<DomainEvent> domainEvents) {
        // automatically creates a stream when events are added to it
        appendEventsToStream(streamName, domainEvents);
    }

    @Override public void appendEventsToStream(String streamName, List<DomainEvent> domainEvents, Integer expectedVersion) {
        var commitId = UUID.randomUUID();

        var eventsInStorageFormat = domainEvents.stream()
                .map(event -> toEventStoreStorageFormat(event, commitId))
                .toList();

        var streamOptions = AppendToStreamOptions.get();
        if (expectedVersion != null) {
            streamOptions.expectedRevision(expectedVersion);
        }
        await(eventStoreDBClient.appendToStream(streamName(streamName), streamOptions, eventsInStorageFormat.iterator()));
    }

    private EventData toEventStoreStorageFormat(Object event, UUID commitId) {
        var metadata = Map.of(
                "commitId", commitId.toString(),
                EVENT_CLR_TYPE_HEADER, event.getClass().toString()
        );

        return EventData.builderAsJson(event.getClass().getTypeName(), event)
                .metadataAsBytes(bytes(metadata))
                .build();
    }

    private byte[] bytes(Map<String, String> metadata) {
        try {
            return objectMapper.writeValueAsBytes(metadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public List<DomainEvent> getStream(String streamName, int fromVersion, int toVersion) {
        // Event Store wants a number of events to retrieve, not the highest version
        var amount = fromVersion == 0 ? toVersion : (toVersion - fromVersion + 1);
        var options = ReadStreamOptions.get()
                .fromStart()
                .fromRevision(fromVersion)
                .maxCount(amount)
                .forwards();

        ReadResult result = await(eventStoreDBClient.readStream(streamName(streamName), options));

        return result.getEvents().stream()
                .map(e -> (DomainEvent) rebuildEvent(e))
                .toList();
    }

    // snapshots in Event Store are just events in dedicated snapshot streams
    // explained: http://stackoverflow.com/questions/16359330/is-snapshot-supported-from-greg-young-eventstore
    @Override public <T> void addSnapshot(String streamName, T snapshot) {
        var stream = snapshotStreamNameFor(streamName);
        var snapshotAsEvent = toEventStoreStorageFormat(snapshot, UUID.randomUUID());

        await(eventStoreDBClient.appendToStream(stream,  snapshotAsEvent));
    }

    @SuppressWarnings("unchecked")
    @Override public <T> T getLatestSnapshot(String streamName) {
        var stream = snapshotStreamNameFor(streamName);
        int amountToFetch = 1;

        var options = ReadStreamOptions.get()
                .fromEnd()
                .backwards()
                .maxCount(amountToFetch);

        ReadResult result = await(eventStoreDBClient.readStream(stream, options));
        if (result.getEvents().isEmpty())
            return null;

        return (T) rebuildEvent(result.getEvents().get(0));
    }

    private Object rebuildEvent(ResolvedEvent resolvedEvent) {
        var metadata = readMap(resolvedEvent);
        String eventType = resolvedEvent.getOriginalEvent().getEventType();

        return parse(resolvedEvent.getOriginalEvent().getEventData(), eventType);
    }

    private Map<String, String> readMap(ResolvedEvent resolvedEvent) {
        try {
            return objectMapper.readValue(resolvedEvent.getOriginalEvent().getUserMetadata(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object parse(byte[] data, String name) {
        try {
            return objectMapper.readValue(data, Class.forName(name));
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String snapshotStreamNameFor(String streamName) {
        // snapshots are just events in separate streams
        return streamName(streamName) + "-snapshots";
    }

    private static String streamName(String streamName) {
        // Get Event Store projections require only a single hypen ("-")
        // see: https://groups.google.com/forum/#!msg/event-store/D477bKLcdI8/62iFGhHdMMIJ
        var sp = streamName.split("-", 2);
        return sp[0] + "-" + sp[1].replace("-", "");
    }

    private static <T> T await(CompletableFuture<T> completableFuture) {
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
