package ch19.listings.application.watching.businessUseCases;

import ch19.listings.model.listings.ListingRepository;
import ch19.listings.model.watchLists.WatchedItemRepository;

import java.util.UUID;

class WatchItemService {

    private ListingRepository listings;
    private WatchedItemRepository watchedItems;
    // private DocumentSession unitOfWork;


    public WatchItemService(ListingRepository listings, WatchedItemRepository watchedItems) {
        this.listings = listings;
        this.watchedItems = watchedItems;
    }

    public void watch(WatchItem command) {
        // Ensure Auction exists
        var item = listings.findBy(command.auctionId());

        var watch = item.watch(UUID.randomUUID(), command.memberId());

        watchedItems.add(watch); // DB will enforce unique constraint on no member watching more than a single item

        // unitOfWork.SaveChanges();
    }

    public void UnWatch(UnWatchItem command) {
        var watchedItem = watchedItems.findBy(command.watchedItemId());

        watchedItems.remove(watchedItem);
    }
}
