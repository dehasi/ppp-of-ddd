package ch19.listings.infrastructure;

public abstract class Entity<TypeId> {
    public TypeId id;
    public int version;
}
