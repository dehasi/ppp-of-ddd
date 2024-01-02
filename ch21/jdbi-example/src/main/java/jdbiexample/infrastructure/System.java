package jdbiexample.infrastructure;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.Objects;

// Replacement for
// var connection = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["AuctionDB"].ConnectionString)
// using (TransactionScope scope = new TransactionScope())
public class System {

    private final Jdbi jdbi;
    private Handle handle;

    public System(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Handle handle() {
        return jdbi.open();
    }

    public synchronized void beginTransaction() {
        if (handle != null) throw new RuntimeException("Transaction has already been started");

        handle = jdbi.open();

        handle = handle.begin();
    }

    public Handle inTransaction() {
        return Objects.requireNonNull(handle, "It seems a transaction wasn't started");
    }

    public synchronized void commit() {
        handle.commit();
        handle.close();
        handle = null;
    }
}
