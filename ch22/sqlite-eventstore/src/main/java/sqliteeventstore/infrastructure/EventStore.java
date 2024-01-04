package sqliteeventstore.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EventStore implements IEventStore {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public EventStore(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override public void createNewStream(String streamName, String type, List<DomainEvent> domainEvents) {
        var eventStream = new EventStream(streamName);

        jdbcTemplate.update("""
                INSERT INTO event_sources (id, version, type)
                VALUES (:id, :version, :type)
                """, Map.of(
                "id", eventStream.id,
                "version", eventStream.version,
                "type", type));

        appendEventsToStream(streamName, domainEvents, null);
    }

    @Override public void appendEventsToStream(String streamName, List<DomainEvent> domainEvents, Integer expectedVersion) {
        var stream = jdbcTemplate.queryForObject("""
                        SELECT * FROM event_sources
                        WHERE id = :streamName
                        """,
                Map.of("streamName", streamName),
                (rs, rowNum) -> new EventStream(rs.getString("id"), rs.getInt("version"))
        );

        // THIS IS NOT THREAD SAFE
        if (expectedVersion != null) checkForConcurrencyError(expectedVersion, stream);
        // Even from another thread can be added here: after "checkForConcurrencyError" and before "store".

        for (var event : domainEvents) {
            EventWrapper eventWrapper = stream.registerEvent(event);

            jdbcTemplate.update("""
                    INSERT INTO events ( id,  version,  name,  event_source_id,  data,  created_at)
                                VALUES (:id, :version, :name, :event_source_id, :data, :created_at)
                    """, Map.of(
                    "id", eventWrapper.getId(),
                    "version", eventWrapper.getEventNumber(),
                    "name", event.getClass().getTypeName(),
                    "event_source_id", streamName,
                    "data", json(eventWrapper.getEvent()),
                    "created_at", LocalDateTime.now()));
        }

        jdbcTemplate.update("""
                UPDATE event_sources
                SET version = :version
                WHERE id = :id
                """, Map.of(
                "id", stream.id,
                "version", stream.version));
    }

    private String json(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override public List<DomainEvent> getStream(String streamName, int fromVersion, int toVersion) {
        return jdbcTemplate.query("""
                        SELECT * FROM events
                        WHERE event_source_id = :streamName
                        AND version >= :fromVersion
                        AND version <= :toVersion
                        ORDER BY version
                        """, Map.of(
                        "streamName", streamName,
                        "fromVersion", fromVersion,
                        "toVersion", toVersion
                ),
                (rs, rowNum) -> (DomainEvent) parse(rs.getString("data"), rs.getString("name")));
    }

    private Object parse(String data, String name) {
        try {
            // Expectation: every event in the table keeps its type
            return objectMapper.readValue(data, Class.forName(name));
        } catch (JsonProcessingException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public <T> void addSnapshot(String streamName, T snapshot) {
        jdbcTemplate.update("""
                INSERT INTO snapshots ( event_source_id,  version,  type,  data,  created_at)
                               VALUES (:event_source_id, :version, :type, :data, :created_at)
                """, Map.of(
                "event_source_id", streamName,
                "version", 42, // snapshot has a version inside
                "type", snapshot.getClass().getTypeName(),
                "data", json(snapshot),
                "created_at", LocalDateTime.now()));
    }

    @SuppressWarnings("unchecked") // We expect a certain type
    @Override public <T> T getLatestSnapshot(String streamName) {
        var latestSnapshot = jdbcTemplate.queryForObject("""
                        SELECT * FROM snapshots
                        WHERE event_source_id = :streamName
                        ORDER BY created_at DESC
                        LIMIT 1
                        """, Map.of(
                        "streamName", streamName
                ),
                (rs, rowNum) -> parse(rs.getString("data"), rs.getString("type")));

        if (latestSnapshot == null) {
            return null; // Maybe make the return type Optional<T>?
        }
        return (T) latestSnapshot;
    }

    private static void checkForConcurrencyError(int expectedVersion, EventStream stream) {
        var lastUpdatedVersion = stream.version;
        if (lastUpdatedVersion != expectedVersion) {
            var message = String.format("Expected: {%s}. Found: {%s}", expectedVersion, lastUpdatedVersion);
            throw new OptimisticConcurrencyException(message);
        }
    }
}
