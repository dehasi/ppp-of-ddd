package gambling.applicationServices.utils;

// I.e. Hibernate Transaction
public class Transaction implements AutoCloseable {
    @Override public void close() {}

    public void complete() {}

    public void rollback() {}

    public static Transaction start() {return new Transaction();}
}
