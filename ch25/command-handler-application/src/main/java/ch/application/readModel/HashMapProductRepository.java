package ch.application.readModel;

import java.util.HashMap;
import java.util.Map;

public class HashMapProductRepository implements ProductRepository {

    private final Map<Integer, Product> database = new HashMap<>();

    @Override public void add(Product product) {
        database.put(product.id(), product);
    }

    @Override public Product find_by(int id) {
        return database.get(id);
    }
}
