package hibernateexample.infrastructure.hibernate;

import jakarta.persistence.AttributeConverter;

import java.util.UUID;

public class UuidConverter implements AttributeConverter<UUID, String> {

    @Override public String convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) return null;
        return uuid.toString();
    }

    @Override public UUID convertToEntityAttribute(String s) {
        if (s == null) return null;
        return UUID.fromString(s);
    }
}
