package ravendbeventstore.infrastructure;

import ravendbeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import ravendbeventstore.model.PayAsYouGo.PayAsYouGoAccount;

import java.util.List;
import java.util.UUID;

public class PayAsYouGoAccountRepository implements IPayAsYouGoAccountRepository {

    private static final String NAME = "PayAsYouGoAccount";

    private final IEventStore eventStore;

    public PayAsYouGoAccountRepository(IEventStore eventStore) {this.eventStore = eventStore;}

    @Override public PayAsYouGoAccount findBy(UUID id) {
        var streamName = String.format("{%s}-{%s}", NAME, id);

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
        var streamName = String.format("{%s}-{%s}", NAME, payAsYouGoAccount.id);

        eventStore.createNewStream(streamName, payAsYouGoAccount.changes);
    }

    @Override public void save(PayAsYouGoAccount payAsYouGoAccount) {
        var streamName = String.format("{%s}-{%s}", NAME, payAsYouGoAccount.id);

        eventStore.appendEventsToStream(streamName, payAsYouGoAccount.changes);
    }
}
