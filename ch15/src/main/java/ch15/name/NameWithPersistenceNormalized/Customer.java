package ch15.name.NameWithPersistenceNormalized;

import java.util.UUID;

public class Customer {

    // I'll use String to make interaction with Hibernate easier.
    // It's possible to use UUID.
    private String id;
    private Name name;

    public Customer(UUID id, Name name) {
        this.id = id.toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    protected Customer setId(String id) {
        this.id = id;
        return this;
    }

    protected Customer setName(Name name) {
        this.name = name;
        return this;
    }
}
