package ravendbeventstore.infrastructure;

import java.time.LocalDateTime;

public record SnapshotWrapper(
        String streamName,
        Object snapshot,
        LocalDateTime created
) {}
