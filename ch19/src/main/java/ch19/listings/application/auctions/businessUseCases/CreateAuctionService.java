package ch19.listings.application.auctions.businessUseCases;

import ch19.listings.model.Money;
import ch19.listings.model.listingFormat.auctions.Auction;
import ch19.listings.model.listingFormat.auctions.AuctionRepository;
import ch19.listings.model.listings.FormatType;
import ch19.listings.model.listings.Listing;
import ch19.listings.model.listings.ListingFormat;
import ch19.listings.model.listings.ListingRepository;
import ch19.listings.model.sellers.ISellerService;

import java.util.UUID;

class CreateAuctionService {

    private final AuctionRepository auctions;
    private final ISellerService sellerService;
    private final ListingRepository listings;
    // private DocumentSession unitOfWork;

    CreateAuctionService(AuctionRepository auctions, ISellerService sellerService, ListingRepository listings) {
        this.auctions = auctions;
        this.sellerService = sellerService;
        this.listings = listings;
    }

    UUID create(AuctionCreation command) {
        var auctionId = UUID.randomUUID();
        var listingId = UUID.randomUUID();
        var startingPrice = new Money(command.startingPrice);

        var listing = new Listing(listingId, command.sellerId, new ListingFormat(auctionId, FormatType.AUCTION));

        var auction = new Auction(auctionId, listingId, startingPrice, command.endsAt);

        // Can seller list
        var seller = sellerService.getSeller(command.sellerId);

        if (seller != null && seller.canList()) {
            listings.add(listing);
            auctions.add(auction);
        }

        // unitOfWork.saveChanges();

        return auctionId;
    }
}
