package hibernateexample.infrastructure;

import java.util.UUID;

public abstract class Entity { // <UUID> { // Hibernate doesn't understand Generics

    public UUID id;
    public int version;

    public UUID getId() {
        return id;
    }

    public Entity setId(UUID id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public Entity setVersion(int version) {
        this.version = version;
        return this;
    }
}
