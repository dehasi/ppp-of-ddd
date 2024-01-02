package jdbiexample.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DomainEvents {

    // Delegate = Consumer
    // Action = Supplier
    // [ThreadStatic] = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<Map<Class<?>, List<Consumer<?>>>> actions = ThreadLocal.withInitial(HashMap::new);

    @SuppressWarnings("unchecked")
    public static <T> void raise(T event) {
        actions.get().getOrDefault(event.getClass(), List.of())
                .stream().map(consumer -> (Consumer<T>) consumer)
                .forEach(consumer -> consumer.accept(event));
    }

    public static <T> DomainEventRegistrationRemover register(Class<T> type, Consumer<T> callback) {
        actions.get().putIfAbsent(type, new ArrayList<>());
        actions.get().get(type).add(callback);

        return new DomainEventRegistrationRemover(() -> actions.get().get(type).remove(callback));
    }

    public static final class DomainEventRegistrationRemover implements AutoCloseable {
        private final Supplier<?> callOnDispose;

        public DomainEventRegistrationRemover(Supplier<?> toCall) {
            callOnDispose = toCall;
        }

        @Override public void close() {
            callOnDispose.get();
        }
    }
}
