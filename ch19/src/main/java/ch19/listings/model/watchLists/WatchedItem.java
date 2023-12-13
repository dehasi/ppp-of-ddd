package ch19.listings.model.watchLists;

import java.util.UUID;

public final class WatchedItem {

    private final UUID id;
    private final UUID listingId;
    private final UUID memberId;
    private String note;

    public WatchedItem(UUID id, UUID listingId, UUID memberId) {
        this.id = id;
        this.listingId = listingId;
        this.memberId = memberId;
    }

    public UUID id() {return id;}

    public UUID listingId() {return listingId;}

    public UUID memberId() {return memberId;}

    public String note() {return note;}

    public WatchedItem note(String note) {
        this.note = note;
        return this;
    }
}
