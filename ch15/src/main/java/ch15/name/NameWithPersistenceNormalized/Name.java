package ch15.name.NameWithPersistenceNormalized;

import ch15.ValueObject;
import me.dehasi.replacements.contotion.Check;
import me.dehasi.replacements.exception.ApplicationException;

import java.util.List;

import static zeliba.the.TheString.the;

public class Name extends ValueObject<Name> {

    // Hibernate
    private String sid;
    private String firstName;
    private String surname;

    // Required by NHibernate
    public Name() {}

    public String getSid() {
        return sid;
    }

    public Name setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public Name(String firstName, String surname) {
        Check.that(the(firstName).isNotEmpty()).onConstraintFailure(() -> {
            throw new ApplicationException("You must specify a first name.");
        });

        Check.that(the(surname).isNotEmpty()).onConstraintFailure(() -> {
            throw new ApplicationException("You must specify a surname.");
        });

        this.firstName = firstName;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    protected Name setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    protected Name setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(firstName, surname);
    }
}
