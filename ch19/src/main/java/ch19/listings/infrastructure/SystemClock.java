package ch19.listings.infrastructure;

import java.time.LocalDateTime;

class SystemClock implements Clock {

    @Override public LocalDateTime time() {
        return LocalDateTime.now();
    }
}
