package jooqexample.infrastructure;

public abstract class Entity<Tid> { // Hibernate doesn't understand Generics

    public Tid id;
    public int version;

    public Tid getId() {
        return id;
    }

    public Entity setId(Tid id) {
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
