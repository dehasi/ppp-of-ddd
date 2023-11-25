package accountmanagement.controllers;

import com.eventstore.dbclient.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
class EventRetriever {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EventStoreDBClient eventStoreDBClient;
    private final String streamName = "BeganFollowing";

    EventRetriever() {
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false");
        this.eventStoreDBClient = EventStoreDBClient.create(settings);
    }

    public List<ResolvedEvent> recentEvents(String stream) {
        var options = ReadStreamOptions.get().forwards().maxCount(20).fromStart();

        try {
            ReadResult results = eventStoreDBClient.readStream(stream, options).get();
            return results.getEvents();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
