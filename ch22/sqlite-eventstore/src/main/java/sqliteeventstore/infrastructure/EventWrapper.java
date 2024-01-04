package sqliteeventstore.infrastructure;

public class EventWrapper {

    private String id;
    private DomainEvent event;
    private int eventNumber;
    private String eventStreamId;

    public EventWrapper() {} // for jackson

    public EventWrapper(DomainEvent event, int eventNumber, String eventStreamId) {
        this.id = String.format("{%s}-{%s}", eventStreamId, eventNumber);
        this.event = event;
        this.eventNumber = eventNumber;
        this.eventStreamId = eventStreamId;
    }

    public String getId() {
        return id;
    }

    public EventWrapper setId(String id) {
        this.id = id;
        return this;
    }

    public DomainEvent getEvent() {
        return event;
    }

    public EventWrapper setEvent(DomainEvent event) {
        this.event = event;
        return this;
    }

    public int getEventNumber() {
        return eventNumber;
    }

    public EventWrapper setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
        return this;
    }

    public String getEventStreamId() {
        return eventStreamId;
    }

    public EventWrapper setEventStreamId(String eventStreamId) {
        this.eventStreamId = eventStreamId;
        return this;
    }
}
