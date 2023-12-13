package ch19.listings.model.listings;

import java.util.UUID;

public interface ListingRepository {

    void add(Listing listing);

    Listing findBy(UUID id);
}
