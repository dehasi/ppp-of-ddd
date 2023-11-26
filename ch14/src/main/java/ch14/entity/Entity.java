package ch14.entity;

abstract class Entity<TId> {

    public final TId id;

    protected Entity(TId id) {this.id = id;}

    public TId getId() {
        return id;
    }

    @SuppressWarnings("unchecked") // it's just an example, not a production implementation.
    @Override public boolean equals(Object obj) {
        if (obj instanceof Entity<?> entity) {
            return this.equals((Entity<TId>) entity);
        }
        return false;
    }

    @Override public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Entity<TId> other) {
        if (other == null) {
            return false;
        }
        return this.id.equals(other.id);
    }
}
