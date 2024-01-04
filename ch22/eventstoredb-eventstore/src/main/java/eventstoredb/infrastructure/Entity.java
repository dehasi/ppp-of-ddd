package eventstoredb.infrastructure;

import java.util.UUID;

public class Entity {

    public UUID id;

    public UUID getId() {
        return id;
    }

    public Entity setId(UUID id) {
        this.id = id;
        return this;
    }
}
