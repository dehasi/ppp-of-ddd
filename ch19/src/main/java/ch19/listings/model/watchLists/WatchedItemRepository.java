package ch19.listings.model.watchLists;

import java.util.UUID;

public interface WatchedItemRepository {

    WatchedItem findBy(UUID id);

    void add(WatchedItem watched);

    void remove(WatchedItem watched);
}
