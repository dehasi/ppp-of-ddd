package accountmanagement.controllers;

import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
public class EventPersister {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EventStoreDBClient eventStoreDBClient;
    private final String streamName = "BeganFollowing";

    public EventPersister() {
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false");
        this.eventStoreDBClient = EventStoreDBClient.create(settings);
    }

    public void persistEvent(Object event) {
        try {
            eventStoreDBClient.appendToStream(
                    streamName, toEventData(UUID.randomUUID(), event, Map.of("CommitId", UUID.randomUUID()))
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private EventData toEventData(UUID eventId, Object event, Map<String, Object> headers) {
        var typeName = event.getClass().getSimpleName();
        byte[] metaData;
        try {
            metaData = objectMapper.writeValueAsString(headers).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return EventData.builderAsJson(eventId, typeName, event).metadataAsBytes(metaData).build();
    }
}
