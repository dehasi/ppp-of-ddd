package eventstoredb.infrastructure;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstoredb.model.PayAsYouGo.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class ImportTestData {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false");

    PayAsYouGoAccount account1;
    PayAsYouGoAccount account2;
    PayAsYouGoAccount account3;
    PayAsYouGoAccount account4;
    PayAsYouGoAccount account5;

    // After running these tests, the events will exist inside the Event Store. You can then
    // access the Web Admin UI and run queries, projections, etc.

    @Test void import_test_data_for_temporal_queries_and_projections_example_in_the_book() {
        var eventStoreDBClient = EventStoreDBClient.create(settings);

        var eventStore = new EventStore(eventStoreDBClient, objectMapper);
        var payAsYouGoAccountRepository = new PayAsYouGoAccountRepository(eventStore);

        create5EmptyAccounts();

        simulateCustomerActivityFor3rdJune();
        simulateCustomerActivityFor4thJune();
        simulateCustomerActivityFor5thJune();

        persistAllUncommittedEvents(payAsYouGoAccountRepository);
    }

    private void create5EmptyAccounts() {
        var account1Id = UUID.randomUUID();
        account1 = new PayAsYouGoAccount(account1Id, new Money(0));

        var account2Id = UUID.randomUUID();
        account2 = new PayAsYouGoAccount(account2Id, new Money(0));

        var account3Id = UUID.randomUUID();
        account3 = new PayAsYouGoAccount(account3Id, new Money(0));

        var account4Id = UUID.randomUUID();
        account4 = new PayAsYouGoAccount(account4Id, new Money(0));

        var account5Id = UUID.randomUUID();
        account5 = new PayAsYouGoAccount(account5Id, new Money(0));
    }

    private void simulateCustomerActivityFor4thJune() {
        // Day of the big marketing promotion

        var startOfDay = LocalDateTime.of(2014, 06, 04, 0, 0, 0);

        var freeCalls = new FreePhoneCallCosting();

        account1.topUp(new Money(20), TestClock(startOfDay.plusHours(9)));
        account1.record(PhoneCall(startOfDay.plusHours(10), 22), freeCalls, null);
        account1.record(PhoneCall(startOfDay.plusHours(10), 15), freeCalls, null);
        account1.record(PhoneCall(startOfDay.plusHours(12), 45), freeCalls, null);
        account1.record(PhoneCall(startOfDay.plusHours(18), 5), freeCalls, null);
        account1.record(PhoneCall(startOfDay.plusHours(19), 7), freeCalls, null);

        account2.topUp(new Money(20), TestClock(startOfDay.plusHours(6)));
        account2.record(PhoneCall(startOfDay.plusHours(19), 120), freeCalls, null);

        account3.topUp(new Money(20), TestClock(startOfDay.plusHours(21)));
        account3.record(PhoneCall(startOfDay.plusHours(21), 24), freeCalls, null);
        account3.record(PhoneCall(startOfDay.plusHours(23), 28), freeCalls, null);

        account4.topUp(new Money(20), TestClock(startOfDay.plusHours(18)));
        account4.record(PhoneCall(startOfDay.plusHours(19), 13), freeCalls, null);
        account4.record(PhoneCall(startOfDay.plusHours(19), 19), freeCalls, null);
        account4.record(PhoneCall(startOfDay.plusHours(20), 7), freeCalls, null);
        account4.record(PhoneCall(startOfDay.plusHours(19), 13), freeCalls, null);

        account5.topUp(new Money(20), TestClock(startOfDay.plusHours(23)));
        account5.record(PhoneCall(startOfDay.plusHours(23), 35), freeCalls, null);
    }

    private void simulateCustomerActivityFor3rdJune() {
        // quiet normal day
        var startOfDay = LocalDateTime.of(2014, 06, 03, 0, 0, 0);

        account1.topUp(new Money(5), TestClock(startOfDay.plusHours(10)));
        account1.record(PhoneCall(startOfDay.plusHours(10), 5), cost(), null);
        account1.record(PhoneCall(startOfDay.plusHours(20), 10), cost(), null);

        account4.topUp(new Money(10), TestClock(startOfDay.plusHours(18)));
        account4.record(PhoneCall(startOfDay.plusHours(18), 7), cost(), null);
    }


    private void simulateCustomerActivityFor5thJune() {
        var startOfDay = LocalDateTime.of(2014, 06, 05, 0, 0, 0);

        account1.record(PhoneCall(startOfDay.plusHours(7), 4), cost(), null);
        account1.record(PhoneCall(startOfDay.plusHours(14), 6), cost(), null);
        account1.record(PhoneCall(startOfDay.plusHours(19), 2), cost(), null);

        account2.record(PhoneCall(startOfDay.plusHours(19), 5), cost(), null);

        account4.record(PhoneCall(startOfDay.plusHours(15), 9), cost(), null);
        account4.record(PhoneCall(startOfDay.plusHours(23), 4), cost(), null);
    }


    private Clock TestClock(LocalDateTime date) {
        return new TestClockThatReturnsFixedDate(date);
    }

    private PhoneCall PhoneCall(LocalDateTime start, int minutes) {
        return new PhoneCall(new PhoneNumber("11111111111"), start.toString(), new Minutes(minutes));
    }

    private PhoneCallCosting cost() {
        return new PhoneCallCosting();
    }

    private void persistAllUncommittedEvents(IPayAsYouGoAccountRepository repo) {
        repo.save(account1);
        repo.save(account2);
        repo.save(account3);
        repo.save(account4);
        repo.save(account5);
    }

    static class TestClockThatReturnsFixedDate implements Clock {
        LocalDateTime stubbedDate;

        public TestClockThatReturnsFixedDate(LocalDateTime date) {
            stubbedDate = date;
        }

        @Override public LocalDateTime time() {
            return stubbedDate;
        }
    }


    public static class FreePhoneCallCosting extends PhoneCallCosting {
        @Override public Money determineCostOfCall(Minutes minutes) {
            return new Money(0);
        }
    }

}
