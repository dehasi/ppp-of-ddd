package ch15.persistence.denormalized;

import ch15.name.NameWithPersistence.Customer;
import ch15.name.NameWithPersistence.Name;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class DenormalizedPersistenceTest {

    private final Session session = createSession();

    @Test void Persisting_denormalized_value_objects() {
        final UUID id = UUID.randomUUID();

        NHibernateTransaction(session -> {
            var name = new Name("Kevin", "Kingston");
            var customer = new Customer(id, name);
            session.save(customer);
        });

        NHibernateTransaction(session -> {
            var customer = session.load(Customer.class, id.toString());

            var name = new Name("Kevin", "Kingston");
            assertThat(name).isEqualTo(customer.getName());
        });
    }

    private void NHibernateTransaction(Consumer<Session> action) {
        Transaction transaction = session.beginTransaction();
        action.accept(session);
        transaction.commit();
        // maybe add catch exception and rollback
    }

    private static Session createSession() {
        Configuration configuration = new Configuration();
        // configuration.addAttributeConverter(new NameValueObjectPersister());

        SessionFactory sessionFactory = configuration
                .configure("denormalized/hibernate.cfg.xml")
                .buildSessionFactory();
        return sessionFactory.openSession();
    }
}
