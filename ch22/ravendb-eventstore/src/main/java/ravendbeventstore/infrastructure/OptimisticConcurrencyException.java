package ravendbeventstore.infrastructure;

class OptimisticConcurrencyException extends RuntimeException {
    public OptimisticConcurrencyException(String message) {
        super((message));
    }
}
