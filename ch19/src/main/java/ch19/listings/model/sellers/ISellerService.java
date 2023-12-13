package ch19.listings.model.sellers;

import java.util.UUID;

public interface ISellerService {

    Seller getSeller(UUID sellerId);
}
