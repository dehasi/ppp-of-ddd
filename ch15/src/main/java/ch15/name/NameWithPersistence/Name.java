package ch15.name.NameWithPersistence;

import ch15.ValueObject;
import me.dehasi.replacements.contotion.Check;
import me.dehasi.replacements.exception.ApplicationException;

import java.util.List;

import static zeliba.the.TheString.the;

public class Name extends ValueObject<Name> {

    private String firstName;
    private String surname;

    public Name() {}

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

    public Name setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Name setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override public String toString() {
        return String.format(
                "firstName:%s;;surname:%s", firstName, surname);
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(firstName, surname);
    }
}
