package sqliteeventstore.infrastructure;

import sqliteeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import sqliteeventstore.model.PayAsYouGo.PayAsYouGoAccount;

import java.util.List;
import java.util.UUID;

public class PayAsYouGoAccountRepository implements IPayAsYouGoAccountRepository {

    // For prod, use getCanonicalName
    private static final String TYPE = PayAsYouGoAccount.class.getSimpleName();

    private final IEventStore eventStore;

    public PayAsYouGoAccountRepository(IEventStore eventStore) {this.eventStore = eventStore;}

    @Override public PayAsYouGoAccount findBy(UUID id) {
        var streamName = id.toString();

        // Check for snapshots

        var fromEventNumber = 0;
        var toEventNumber = Integer.MAX_VALUE;

        List<DomainEvent> stream = eventStore.getStream(streamName, fromEventNumber, toEventNumber);
        var payAsYouGoAccount = new PayAsYouGoAccount();
        for (DomainEvent event : stream) {
            payAsYouGoAccount.apply(event);
        }
        return payAsYouGoAccount;
    }

    @Override public void add(PayAsYouGoAccount payAsYouGoAccount) {
        var streamName = payAsYouGoAccount.id.toString();

        eventStore.createNewStream(streamName, TYPE, payAsYouGoAccount.changes);
    }

    @Override public void save(PayAsYouGoAccount payAsYouGoAccount) {
        var streamName = payAsYouGoAccount.id.toString();

        eventStore.appendEventsToStream(streamName, payAsYouGoAccount.changes);
    }
}
