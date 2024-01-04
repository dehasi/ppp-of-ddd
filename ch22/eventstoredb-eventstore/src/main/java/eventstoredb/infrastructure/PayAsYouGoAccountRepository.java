package eventstoredb.infrastructure;

import eventstoredb.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import eventstoredb.model.PayAsYouGo.PayAsYouGoAccount;

import java.util.List;
import java.util.UUID;

public class PayAsYouGoAccountRepository implements IPayAsYouGoAccountRepository {

    // For prod, use getCanonicalName
    private static final String TYPE = PayAsYouGoAccount.class.getSimpleName();

    private final IEventStore eventStore;

    public PayAsYouGoAccountRepository(IEventStore eventStore) {this.eventStore = eventStore;}

    @Override public PayAsYouGoAccount findBy(UUID id) {
        var streamName = streamName(id);

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
        var streamName = streamName(payAsYouGoAccount.id);

        eventStore.createNewStream(streamName, payAsYouGoAccount.changes);
    }

    @Override public void save(PayAsYouGoAccount payAsYouGoAccount) {
        var streamName = streamName(payAsYouGoAccount.id);

        eventStore.appendEventsToStream(streamName, payAsYouGoAccount.changes);
    }

    private static String streamName(UUID id) {
        return String.format("{%s}-{%s}", TYPE, id.toString());
    }
}
