package ravendbeventstore;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import ravendbeventstore.application.BusinessUseCases.CreateAccount;
import ravendbeventstore.application.BusinessUseCases.RecordPhoneCall;
import ravendbeventstore.application.BusinessUseCases.TopUpCredit;
import ravendbeventstore.infrastructure.*;
import ravendbeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;

import java.util.HashMap;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup() {
        Map<Class<?>, Object> beans = new HashMap<>();

        IDocumentSession documentSession = createSession();
        Clock clock = new SystemClock();
        IEventStore eventStore = new EventStore(documentSession);
        IPayAsYouGoAccountRepository payAsYouGoAccountRepository = new PayAsYouGoAccountRepository(eventStore);

        CreateAccount createAccount = new CreateAccount(payAsYouGoAccountRepository, documentSession);
        RecordPhoneCall recordPhoneCall = new RecordPhoneCall(payAsYouGoAccountRepository, documentSession, clock);
        TopUpCredit topUpCredit = new TopUpCredit(payAsYouGoAccountRepository, documentSession, clock);

        beans.put(eventStore.getClass(), eventStore);
        beans.put(createAccount.getClass(), createAccount);
        beans.put(recordPhoneCall.getClass(), recordPhoneCall);
        beans.put(topUpCredit.getClass(), topUpCredit);

        return new ObjectFactory(beans);
    }

    private static IDocumentSession createSession() {
        var documentStore = new DocumentStore("http://localhost:8080", "ch22");
        documentStore.initialize();
        documentStore.getConventions().getEntityMapper().registerModule(new JavaTimeModule());

        IDocumentSession session = documentStore.openSession();
        session.advanced().setUseOptimisticConcurrency(true);

        return session;
    }

    @SuppressWarnings("unchecked")
    public static class ObjectFactory {
        private final Map<Class<?>, Object> beans;

        private ObjectFactory(Map<Class<?>, Object> beans) {this.beans = beans;}

        public <T> T getInstance(Class<T> clazz) {
            return (T) beans.get(clazz);
        }
    }
}
