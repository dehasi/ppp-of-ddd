package hibernateexample.infrastructure.hibernate;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class CustomUuidGenerator implements IdentifierGenerator {

    @Override public Object generate(SharedSessionContractImplementor session, Object object) {
        return UUID.randomUUID();
    }
}
