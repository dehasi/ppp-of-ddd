package ch16.datastore;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class DatastoreIdGenerationExampleTest {

    private final Session session = createSession();

    @Test void id_is_set_by_datastore_via_ORM() {
        var entity1 = new IdTestEntity();
        var entity2 = new IdTestEntity();

        // initially no id
        assertThat(0).isEqualTo(entity1.getId());
        assertThat(0).isEqualTo(entity2.getId());

        NHibernateTransaction(session -> {
            session.save(entity1);
            session.save(entity2);
        });

        // id will have been set via NHibernate
        assertThat(1).isEqualTo(entity1.getId());
        assertThat(2).isEqualTo(entity2.getId());
    }

    private void NHibernateTransaction(Consumer<Session> action) {
        Transaction transaction = session.beginTransaction();
        action.accept(session);
        transaction.commit();
        // maybe add catch exception and rollback
    }

    private static Session createSession() {
        Configuration configuration = new Configuration();

        SessionFactory sessionFactory = configuration
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        return sessionFactory.openSession();
    }
}
