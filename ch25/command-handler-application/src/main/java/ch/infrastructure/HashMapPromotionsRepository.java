package ch.infrastructure;

import ch.model.PromotionsNamespace.IPromotionsRepository;
import ch.model.PromotionsNamespace.Promotion;

import java.util.HashMap;
import java.util.Map;

public class HashMapPromotionsRepository implements IPromotionsRepository {

    private final Map<String, Promotion> database = new HashMap<>();

    @Override public void add(Promotion promotion) {
        database.put(promotion.voucher_code(), promotion);
    }

    @Override public Promotion find_by(String voucher_code) {
        return database.get(voucher_code);
    }
}
