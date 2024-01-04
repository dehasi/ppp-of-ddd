package eventstoredb.infrastructure;

public final class EventStream {

    public final String id; //aggregate type + id
    public int version;

    public EventStream(String id) {
        this.id = id;
    }

    public EventStream(String id, int version) {
        this.id = id;
        this.version = version;
    }

    public EventWrapper registerEvent(DomainEvent event) {
        version++;
        return new EventWrapper(event, version, id);
    }

}
