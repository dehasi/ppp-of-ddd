package jooqexample.infrastructure;

public abstract class ValueObject<T extends ValueObject<T>> {

    protected abstract Iterable<Object> getAttributesToIncludeInEqualityCheck();

    @Override public int hashCode() {
        int hash = 17;
        for (var obj : this.getAttributesToIncludeInEqualityCheck())
            hash = hash * 31 + (obj == null ? 0 : obj.hashCode());
        return hash;
    }

    @Override public boolean equals(Object obj) {
        // Generics work differently in Java and C#
        if (obj != null && !(this.getClass().isAssignableFrom(obj.getClass()))) {
            return false;
        } else {
            return equals((T) obj);
        }
    }

    public boolean equals(T other) {
        if (other == null) {
            return false;
        }

        // SequenceEqual
        return getAttributesToIncludeInEqualityCheck().equals(other.getAttributesToIncludeInEqualityCheck());
    }
}
