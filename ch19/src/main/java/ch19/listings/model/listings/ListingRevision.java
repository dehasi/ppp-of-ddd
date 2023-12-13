package ch19.listings.model.listings;

import java.time.LocalDateTime;
import java.util.UUID;

record ListingRevision(UUID item, LocalDateTime date, String revisedInformation) {}
