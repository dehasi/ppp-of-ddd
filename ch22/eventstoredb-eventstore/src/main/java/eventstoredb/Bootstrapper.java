package eventstoredb;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eventstoredb.application.BusinessUseCases.CreateAccount;
import eventstoredb.application.BusinessUseCases.RecordPhoneCall;
import eventstoredb.application.BusinessUseCases.TopUpCredit;
import eventstoredb.infrastructure.*;
import eventstoredb.model.PayAsYouGo.IPayAsYouGoAccountRepository;

import java.util.HashMap;
import java.util.Map;

public class Bootstrapper {

    public static ObjectFactory startup() {
        Map<Class<?>, Object> beans = new HashMap<>();

        Clock clock = new SystemClock();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        IEventStore eventStore = new EventStore(createDBClient(), objectMapper);
        IPayAsYouGoAccountRepository payAsYouGoAccountRepository = new PayAsYouGoAccountRepository(eventStore);

        CreateAccount createAccount = new CreateAccount(payAsYouGoAccountRepository);
        RecordPhoneCall recordPhoneCall = new RecordPhoneCall(payAsYouGoAccountRepository, clock);
        TopUpCredit topUpCredit = new TopUpCredit(payAsYouGoAccountRepository, clock);

        beans.put(eventStore.getClass(), eventStore);
        beans.put(createAccount.getClass(), createAccount);
        beans.put(recordPhoneCall.getClass(), recordPhoneCall);
        beans.put(topUpCredit.getClass(), topUpCredit);

        return new ObjectFactory(beans);
    }

    private static EventStoreDBClient createDBClient() {
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false");
        EventStoreDBClient eventStoreDBClient = EventStoreDBClient.create(settings);
        return eventStoreDBClient;
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
