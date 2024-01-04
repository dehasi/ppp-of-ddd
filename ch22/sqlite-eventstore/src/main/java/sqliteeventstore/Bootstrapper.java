package sqliteeventstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.sqlite.SQLiteDataSource;
import sqliteeventstore.application.BusinessUseCases.CreateAccount;
import sqliteeventstore.application.BusinessUseCases.RecordPhoneCall;
import sqliteeventstore.application.BusinessUseCases.TopUpCredit;
import sqliteeventstore.infrastructure.*;
import sqliteeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;

import java.util.HashMap;
import java.util.Map;

public class Bootstrapper {
    // spring.datasource.url=jdbc:sqlite:your_database_name_here.db
    public static ObjectFactory startup(String url) {
        Map<Class<?>, Object> beans = new HashMap<>();

        NamedParameterJdbcTemplate jdbcTemplate = jdbcTemplate(url);
        Clock clock = new SystemClock();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        IEventStore eventStore = new EventStore(jdbcTemplate, objectMapper);
        IPayAsYouGoAccountRepository payAsYouGoAccountRepository = new PayAsYouGoAccountRepository(eventStore);

        CreateAccount createAccount = new CreateAccount(payAsYouGoAccountRepository, jdbcTemplate);
        RecordPhoneCall recordPhoneCall = new RecordPhoneCall(payAsYouGoAccountRepository, jdbcTemplate, clock);
        TopUpCredit topUpCredit = new TopUpCredit(payAsYouGoAccountRepository, jdbcTemplate, clock);

        beans.put(eventStore.getClass(), eventStore);
        beans.put(createAccount.getClass(), createAccount);
        beans.put(recordPhoneCall.getClass(), recordPhoneCall);
        beans.put(topUpCredit.getClass(), topUpCredit);

        return new ObjectFactory(beans);
    }

    private static NamedParameterJdbcTemplate jdbcTemplate(String url) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        return new NamedParameterJdbcTemplate(dataSource);
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
