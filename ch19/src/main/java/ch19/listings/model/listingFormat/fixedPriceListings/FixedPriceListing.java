package ch19.listings.model.listingFormat.fixedPriceListings;

import ch19.listings.model.Money;
import ch19.listings.model.listings.Listing;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class FixedPriceListing {

    private UUID id;
    private UUID sellerId;
    private Listing listing;
    private LocalDateTime endsAt;
    private Money buyNowPrice;

    public FixedPriceListing(UUID id, UUID sellerId, Listing listing, LocalDateTime endsAt, Money buyNowPrice) {
        this.id = requireNonNull(id);
        this.sellerId = requireNonNull(sellerId);
        this.listing = requireNonNull(listing);
        this.endsAt = requireNonNull(endsAt);
        this.buyNowPrice = requireNonNull(buyNowPrice);
    }

    public void BestOffer() {
        // http://ocsnext.ebay.co.uk/ocs/sc
        // for Buy It Now listing only
    }
}
