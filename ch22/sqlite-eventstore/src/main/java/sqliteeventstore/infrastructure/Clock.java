package sqliteeventstore.infrastructure;

import java.time.LocalDateTime;

public interface Clock {

    LocalDateTime time();
}
