package ch16.datastore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
class IdTestEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public IdTestEntity setId(int id) {
        this.id = id;
        return this;
    }
}
