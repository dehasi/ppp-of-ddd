package ReturnEvents.infrastructure;

public interface EventDispatcher {
    <T> void dispatch(T evnt);
}
