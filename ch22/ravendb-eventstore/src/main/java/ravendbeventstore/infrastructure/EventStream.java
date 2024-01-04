package ravendbeventstore.infrastructure;

public class EventStream {

    public String id; //aggregate type + id
    public int version;

    private EventStream() {} // why? for ORM?

    public EventStream(String id) {
        this.id = id;
    }

    public EventWrapper registerEvent(DomainEvent event) {
        version++;
        return new EventWrapper(event, version, id);
    }

    // for Jackson
    public String getId() {
        return id;
    }

    public EventStream setId(String id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public EventStream setVersion(int version) {
        this.version = version;
        return this;
    }
}
