package ch15.persistence.denormalized;

import ch15.name.NameWithPersistence.Name;

import javax.persistence.AttributeConverter;

// NHibernate customisation for custom mapping logic
// Class for Denormalized_persistence_example
// Hibernate can't find it as an inner class
public class NameValueObjectPersister implements AttributeConverter<Name, String> {

    @Override public String convertToDatabaseColumn(Name attribute) {
        return attribute.toString();
    }

    @Override public Name convertToEntityAttribute(String dbData) {
        var parts = dbData.split(";;");

        var firstName = parts[0].split(":")[1];
        var surName = parts[1].split(":")[1];

        return new Name(firstName, surName);
    }
}
