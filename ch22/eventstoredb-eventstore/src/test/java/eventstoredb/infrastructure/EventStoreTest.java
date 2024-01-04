package eventstoredb.infrastructure;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.eventstore.dbclient.WrongExpectedVersionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class EventStoreTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<DomainEvent> testEvents = List.of(
            new TestEvent(UUID.randomUUID())
                    .setName("Name 0")
                    .setHandle("Jimmy"),
            new TestEvent(UUID.randomUUID())
                    .setName("Name 0")
                    .setHandle("Trevor"),
            new TestEvent(UUID.randomUUID())
                    .setName("Name 0")
                    .setHandle("Suzie"));

    private final EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false");

    @Test void events_are_persisted_and_can_be_retrieved() {
        newConnection(eventStoreDBClient -> {
            var eventStore = new EventStore(eventStoreDBClient, objectMapper);
            var streamName = createDisposableStreamName();
            eventStore.appendEventsToStream(streamName, testEvents, null);

            var fromEs = eventStore.getStream(streamName, 0, Integer.MAX_VALUE - 1);

            assertThat(fromEs.size()).isEqualTo(testEvents.size());
            assertThat(fromEs).containsExactlyElementsOf(testEvents);
        });
    }

    @Test void a_subset_of_events_can_be_retrieved_by_querying_on_version_numbers() {
        newConnection(eventStoreDBClient -> {
            var eventStore = new EventStore(eventStoreDBClient, objectMapper);
            var streamName = createDisposableStreamName();
            eventStore.appendEventsToStream(streamName, testEvents, null);

            var fromEs = eventStore.getStream(streamName, 1, 1);

            assertThat(fromEs).hasSize(1);
            assertThat(fromEs.get(0))
                    .extracting(e -> (TestEvent) e)
                    .extracting(t -> t.handle).isEqualTo("Trevor");

        });
    }

//    @Disabled("It seems backwards doesn't work")
    @Test void snapshots_are_persisted_and_the_latest_one_is_always_returned() {
        newConnection(eventStoreDBClient -> {
            var eventStore = new EventStore(eventStoreDBClient, objectMapper);
            var streamName = createDisposableStreamName();

            var snapshot1 = new TestSnapshot().setVersion(1);
            var snapshot2 = new TestSnapshot().setVersion(2);
            var snapshot3 = new TestSnapshot().setVersion(3);

            eventStore.addSnapshot(streamName, snapshot1);
            eventStore.addSnapshot(streamName, snapshot2);
            eventStore.addSnapshot(streamName, snapshot3);

            var fromEs = eventStore.<TestSnapshot>getLatestSnapshot(streamName);

            assertThat(fromEs).isEqualTo(snapshot3);
        });
    }

    @Test void optimistic_concurrency_is_supported() {
        newConnection(eventStoreDBClient -> {
            var eventStore = new EventStore(eventStoreDBClient, objectMapper);
            var streamName = createDisposableStreamName();

            // version number will be 2 (3 events, 0 based)
            eventStore.appendEventsToStream(streamName, testEvents, null);

            var newEvent = new TestEvent(UUID.randomUUID())
                    .setName("New")
                    .setHandle("NewNew");

            int expectedVersion = 1; // we know the version is already 2
            Assertions.assertThatThrownBy(() ->
                    eventStore.appendEventsToStream(streamName, List.of(newEvent), expectedVersion)
            ).hasRootCauseInstanceOf(WrongExpectedVersionException.class);
        });
    }

    //    private EventStoreDBClient eventStoreDBClient = EventStoreDBClient.create(settings);
    private static String createDisposableStreamName() {
        // ES will not allow deleted streams to be re-created
        // for more info: https://groups.google.com/forum/#!msg/event-store/HnJcubREozE/shw_qCwvJ5IJ
        return "teststream" + UUID.randomUUID();
    }

    private void newConnection(Consumer<EventStoreDBClient> action) {
        var eventStoreDBClient = EventStoreDBClient.create(settings);
        action.accept(eventStoreDBClient);
    }


    static class TestEvent extends DomainEvent {
        private String name;
        private String handle;

        public TestEvent() {} // for Jackson

        public TestEvent(UUID id) {
            super(id);
        }

        public String getName() {
            return name;
        }

        public TestEvent setName(String name) {
            this.name = name;
            return this;
        }

        public String getHandle() {
            return handle;
        }

        public TestEvent setHandle(String handle) {
            this.handle = handle;
            return this;
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestEvent testEvent)) return false;

            return Objects.equals(id, testEvent.id)
                    && Objects.equals(name, testEvent.name)
                    && Objects.equals(handle, testEvent.handle);
        }

        @Override public int hashCode() {
            return Objects.hash(id, name, handle);
        }
    }

    static class TestSnapshot {
        public int version;

        public int getVersion() {
            return version;
        }

        public TestSnapshot setVersion(int version) {
            this.version = version;
            return this;
        }


        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestSnapshot that)) return false;

            return version == that.version;
        }

        @Override public int hashCode() {
            return version;
        }
    }
}