package ch15.persistence.normalized;

import ch15.name.NameWithPersistenceNormalized.Customer;
import ch15.name.NameWithPersistenceNormalized.Name;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class NormalizedPersistenceExampleTest {

    private final Session session = createSession();

    @Test void Persisting_normalized_value_objects() {
        final UUID id = UUID.randomUUID();

        NHibernateTransaction(session -> {
            var name = new Name("Kevin", "Kingston");
            var customer = new Customer(id, name);
            session.save(customer);
        });

        // assert that Name has a separate table
        NHibernateTransaction(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Name> cq = cb.createQuery(Name.class);
            Root<Name> rootEntry = cq.from(Name.class);
            CriteriaQuery<Name> all = cq.select(rootEntry);
            TypedQuery<Name> allQuery = session.createQuery(all);
            List<Name> resultList = allQuery.getResultList();

            assertThat(resultList).isNotEmpty();
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
    }

    private static Session createSession() {
        Configuration configuration = new Configuration();

        SessionFactory sessionFactory = configuration
                .configure("normalized/hibernate.cfg.xml")
                .buildSessionFactory();
        return sessionFactory.openSession();
    }
}
